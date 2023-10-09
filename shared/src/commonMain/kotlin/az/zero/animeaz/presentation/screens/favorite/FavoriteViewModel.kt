package az.zero.animeaz.presentation.screens.favorite

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.domain.model.FavAnime
import az.zero.animeaz.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class FavoriteViewModel : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()

    val favAnimeList = animeRepository.getAllFavouriteAnimeList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    fun removeFromFavourite(anime: FavAnime){
        viewModelScope.launch {
            animeRepository.deleteAnime(anime.id)
        }
    }

}