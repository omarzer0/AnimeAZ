package az.zero.animeaz.presentation.screens.favorite

import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.domain.model.FavAnime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.animeaz.util.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class FavoriteViewModel : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()
    private val globalScope: CoroutineScope by inject(named(Constants.GLOBAL_SCOPE))
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    val favAnimeList = animeRepository.getAllFavouriteAnimeList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


    fun removeFromFavourite(anime: FavAnime) {
        globalScope.launch(exceptionHandler) { animeRepository.deleteAnime(anime.id) }
    }

}