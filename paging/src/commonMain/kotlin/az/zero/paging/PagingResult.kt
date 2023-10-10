package az.zero.paging

data class PagingResult<Item>(
    val items: List<Item> = emptyList(),
    val isLoadingMorePages: Boolean = false,
    val isInitialLoading: Boolean = false,
    val noMoreItemsToLoad: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val loadingMoreError: Throwable? = null
) {
    val shouldNotLoadMore get() = isLoadingMorePages || noMoreItemsToLoad || initialLoadingError != null || items.isEmpty()
    val shouldLoadMore get() = !shouldNotLoadMore

    val isLoading get() = isInitialLoading || isLoadingMorePages

}
