package az.zero.animeaz.presentation.screens.search

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.paging.Pager
import io.github.xxfast.decompose.router.SavedStateHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var job: Job? = null

    private val pagination: Pager<Anime, Int> = Pager(
        comparator = { oldItem, newItem -> oldItem.id == newItem.id },
        initialList = emptyList(),
        initialPage = 1,
        howToLoadNext = { it + 1 }
    )

    val searchScreenState = pagination.pagingResultFlow.map {
        SearchScreenState(
            animeList = it.items,
            isInitialLoading = it.isInitialLoading,
            isLoadingMore = it.isLoadingMorePages,
            initialLoadingError = it.initialLoadingError,
            loadingMoreError = it.loadingMoreError
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SearchScreenState()
    )

    init {
        viewModelScope.launch {
            _searchQuery
//                .debounce(500)
                .collectLatest {
                    getSearchData(it.trim())
                }
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }


    private fun getSearchData(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            pagination.loadFirstPage { animeRepository.searchForAnime(query.trim(), it) }
        }
    }

    fun loadMore() {
        job?.cancel()
        job = viewModelScope.launch { pagination.loadNextPage() }
    }

}

data class SearchScreenState(
    val animeList: List<Anime> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val loadingMoreError: Throwable? = null
)