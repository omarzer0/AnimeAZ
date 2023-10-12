package az.zero.animeaz.data.usecase

import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AnimeFavClickUseCase(
    private val globalScope: CoroutineScope,
    private val client: HttpClient,
    private val imageStorageHandler: ImageStorageHandler,
    private val animeRepository: AnimeRepository
) {

    operator fun invoke(anime: Anime) {
        globalScope.launch {
            val isFav = animeRepository.isAnimeFavoriteById(anime.id).firstOrNull() ?: false
            if (isFav) removeAnimeFromFav(anime)
            else addAnimeToFav(anime)
        }
    }

    private suspend fun addAnimeToFav(anime: Anime) {
        val imageByteArray = downloadImageFromString(anime.cover)
            .catch {
                println("SaveImageTest: ${it.message}")
            }
            .filterNotNull()
            .firstOrNull() ?: return

        globalScope.launch { imageStorageHandler.saveImage(anime.id, imageByteArray) }
        globalScope.launch { animeRepository.saveAnimeAsFavourite(anime) }
    }

    private suspend fun removeAnimeFromFav(anime: Anime) {
        animeRepository.deleteAnime(anime.id)
    }

    private fun downloadImageFromString(link: String) = flow {
        val imageBytes = client.get(link).readBytes()
        emit(imageBytes)
    }

}