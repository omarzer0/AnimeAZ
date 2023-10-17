package az.zero.paging

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.sync.Mutex

class Pager<Item, PageType>(
    private val initialPage: PageType,
    private val howToLoadNext: (PageType) -> PageType,
    private val comparator: (oldItem: Item, newItem: Item) -> Boolean,
    private val initialList: List<Item> = emptyList()
) {
    private var page: PageType = initialPage
    private var dataSource: (suspend (pageToLoad: PageType) -> List<Item>)? = null
    private val lockMutex = Mutex()
    private val _pagingResult = MutableStateFlow<PagingResult<Item>>(PagingResult())
    val pagingResult = _pagingResult.asStateFlow()

    val pagingResultFlow = channelFlow {
        _pagingResult.collectLatest { send(it) }
        awaitClose()
    }

    suspend fun loadFirstPage(dataSource: suspend (pageToLoad: PageType) -> List<Item>) {
        handleLock {
            this.dataSource = dataSource
            page = initialPage
            _pagingResult.value = PagingResult(
                items = initialList,
                isLoadingMorePages = false,
                isInitialLoading = true,
                noMoreItemsToLoad = false,
                initialLoadingError = null,
                loadingMoreError = null,
            )

            try {
                val items = dataSource(page)
                _pagingResult.value = _pagingResult.value.copy(
                    items = items,
                    isInitialLoading = false
                )
            } catch (e: Exception) {
                _pagingResult.value = _pagingResult.value.copy(
                    initialLoadingError = e.cause
                        ?: Throwable("Unknown error when loading initial page"),
                    loadingMoreError = null,
                    isInitialLoading = false
                )
            }
        }
    }

    suspend fun loadNextPage() {
        handleLock {
            if (_pagingResult.value.shouldNotLoadMore || dataSource == null) return@handleLock
            _pagingResult.value = _pagingResult.value.copy(
                isLoadingMorePages = true,
                isInitialLoading = false,
                loadingMoreError = null
            )
            val currentPage = page
            try {
                val currentList = _pagingResult.value.items
                page = howToLoadNext(page)
                val items = dataSource?.invoke(page) ?: emptyList()
                // remove already exist items
                val newItems = items.filter { newItem ->
                    val existsItem = currentList.firstOrNull { comparator(newItem, it) }
                    existsItem == null
                }
                val newList = currentList.plus(newItems)

                _pagingResult.value = _pagingResult.value.copy(
                    isLoadingMorePages = false,
                    noMoreItemsToLoad = newItems.isEmpty(),
                    items = newList
                )

            } catch (e: Exception) {
                _pagingResult.value = _pagingResult.value.copy(
                    loadingMoreError = e.cause
                        ?: Throwable("Unknown error when loading next page"),
                    initialLoadingError = null,
                    isLoadingMorePages = false,
                )
                page = currentPage
            }
        }

    }

    suspend fun refresh() {
        handleLock {
            if (dataSource == null) throw Exception("Can't call refresh before loadFirst. please call loadFirst to load first time")
            if (_pagingResult.value.isLoading) return@handleLock
            page = initialPage
            _pagingResult.value = PagingResult(
                items = initialList,
                isLoadingMorePages = false,
                isInitialLoading = false,
                noMoreItemsToLoad = false,
                initialLoadingError = null,
                loadingMoreError = null,
                refreshingError = null,
                isRefreshing = true
            )

            try {
                val items = dataSource!!.invoke(page)
                _pagingResult.value = _pagingResult.value.copy(
                    items = items,
                    isRefreshing = false,
                    refreshingError = null
                )
            } catch (e: Exception) {
                _pagingResult.value = _pagingResult.value.copy(
                    refreshingError = e.cause ?: Throwable("Unknown error when refreshing page"),
                    isRefreshing = false
                )
            }
        }
    }

    private suspend fun handleLock(action: suspend () -> Unit) {
        lockMutex.lock()
        action()
        lockMutex.unlock()
    }
}