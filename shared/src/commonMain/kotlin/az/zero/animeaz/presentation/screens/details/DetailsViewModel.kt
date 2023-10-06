package az.zero.animeaz.presentation.screens.details

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.data.usecase.AddAnimeToFavorite
import az.zero.animeaz.domain.model.Anime
import io.github.xxfast.decompose.router.SavedStateHandle
import org.koin.core.component.inject

class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val addAnimeToFavorite : AddAnimeToFavorite by inject()
    fun onFavoriteClick(anime: Anime) {
        addAnimeToFavorite(anime)
    }

}