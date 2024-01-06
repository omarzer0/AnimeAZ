package az.zero.animeaz.presentation.screens.details

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.domain.usecase.AnimeFavClickUseCase
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import io.github.xxfast.decompose.router.SavedStateHandle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.inject

class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val anime: Anime
) : BaseViewModel() {
    private val animeRepository: AnimeRepository by inject()
    private val animeFavClickUseCase: AnimeFavClickUseCase by inject()

    val isFav = animeRepository.isAnimeFavoriteByIdFlow(anime.id).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun onFavoriteClick() {
        animeFavClickUseCase(anime)
    }

}