package az.zero.animeaz.presentation.screens.home

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.domain.repository.AnimeRepository
import io.github.xxfast.decompose.router.SavedStateHandle
import org.koin.core.component.inject

class HomeViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()

    val animeList = animeRepository.getTopAnimeList(page = 1)

}