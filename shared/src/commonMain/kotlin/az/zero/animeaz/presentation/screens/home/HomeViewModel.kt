package az.zero.animeaz.presentation.screens.home

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.data.local.preferences.Preferences
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.paging.Pager
import io.github.xxfast.decompose.router.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()

    private val _useBioAuth = MutableStateFlow(Preferences.getBioAuthLock())
    // FIXME: updating this blocks the UI until it saves the data and retrieve it back ... Do async update and loading in the Drawer UI
    val useBioAuth = _useBioAuth.asStateFlow()

    private val pagination: Pager<Anime, Int> = Pager(
        comparator = { oldItem, newItem -> oldItem.id == newItem.id },
        initialList = emptyList(),
        initialPage = 1,
        howToLoadNext = { it + 1 }
    )

    val animeListState = pagination.pagingResultFlow.map {
        HomeScreenState(
            animeList = it.items,
            isInitialLoading = it.isInitialLoading,
            isLoadingMore = it.isLoadingMorePages,
            initialLoadingError = it.initialLoadingError,
            loadingMoreError = it.loadingMoreError
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeScreenState()
    )

    init {
        viewModelScope.launch {
            pagination.loadFirstPage { animeRepository.getTopAnimeList(page = it) }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }

    fun onUseBioAuthChanged(useBioAuth: Boolean) {
        Preferences.saveBioAuthLock(useBioAuth)
        _useBioAuth.value = useBioAuth
    }
}

data class HomeScreenState(
    val animeList: List<Anime> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val loadingMoreError: Throwable? = null
)