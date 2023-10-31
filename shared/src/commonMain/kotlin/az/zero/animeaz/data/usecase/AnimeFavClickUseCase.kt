package az.zero.animeaz.data.usecase

import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeFavClickUseCase(
    private val globalScope: CoroutineScope,
    private val client: HttpClient,
    private val imageStorageHandler: ImageStorageHandler,
    private val animeRepository: AnimeRepository
) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    operator fun invoke(anime: Anime) {
        globalScope.launch(exceptionHandler) {
            val isFav = animeRepository.isAnimeFavoriteById(anime.id)
            if (isFav) removeAnimeFromFav(anime)
            else addAnimeToFav(anime)
        }
    }

    private suspend fun addAnimeToFav(anime: Anime) {
        val imageByteArray = downloadImageFromString(anime.cover)
        imageStorageHandler.saveImage(anime.id, imageByteArray)
        animeRepository.saveAnimeAsFavourite(anime)
    }

    private suspend fun removeAnimeFromFav(anime: Anime) {
        animeRepository.deleteAnime(anime.id)
    }

    private suspend fun downloadImageFromString(link: String): ByteArray {
        return client.get(link).readBytes()
    }

}