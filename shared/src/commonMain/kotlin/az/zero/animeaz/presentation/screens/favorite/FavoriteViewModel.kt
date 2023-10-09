package az.zero.animeaz.presentation.screens.favorite

import androidx.compose.ui.graphics.ImageBitmap
import az.zero.animeaz.core.BaseViewModel
import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import io.github.xxfast.decompose.router.SavedStateHandle
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.koin.core.component.inject

class FavoriteViewModel : BaseViewModel() {

    private val animeRepository: AnimeRepository by inject()
    private val imageStorageHandler: ImageStorageHandler by inject()

    val favAnimeList = animeRepository.getAllFavouriteAnimeList().map {
        it.map { anime ->
            supervisorScope {
                async {
                    val imageBitmap = imageStorageHandler.getImage(anime.cover) ?: return@async null
                    FavAnime(
                        id = anime.id,
                        name = anime.name,
                        image = imageBitmap,
                        score = anime.score,
                        reviewCount = anime.reviewCount,
                        rank = anime.rank,
                        popularity = anime.popularity,
                        airingStatus = anime.airingStatus,
                        description = anime.description,
                        season = anime.season,
                        year = anime.year,
                        numberOfEpisodes = anime.numberOfEpisodes,
                        showType = anime.showType
                    )
                }
            }
        }.map {
            it.await()
        }.filterNotNull()
    }.stateIn(
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


data class FavAnime(
    val id: Long,
    val name: String,
    val image: ImageBitmap,
    val score: Float,
    val reviewCount: Long,
    val rank: Long,
    val popularity: Long,
    val airingStatus: Boolean,
    val description: String,
    val season: String,
    val year: String,
    val numberOfEpisodes: Long,
    val showType: String
)