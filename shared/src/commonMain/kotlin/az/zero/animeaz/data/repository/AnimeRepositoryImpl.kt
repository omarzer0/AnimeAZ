package az.zero.animeaz.data.repository

import az.zero.animeaz.data.local.file_storage.ImageStorageHandler
import az.zero.animeaz.data.remote.AnimeRemoteService
import az.zero.animeaz.data.remote.model.toAnimeList
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.model.FavAnime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.animeaz.util.Constants.FILTER_ADULT_CONTENT
import az.zero.animeaz.util.Constants.LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(
    private val animeRemoteService: AnimeRemoteService,
    private val animeDatabaseSource: AnimeDatabaseSource,
    private val imageStorageHandler: ImageStorageHandler
) : AnimeRepository {
    override suspend fun getTopAnimeList(page: Int): List<Anime> {
        return animeRemoteService.getTopAnimeList(page, LIMIT, FILTER_ADULT_CONTENT)
            .animeList
            .toAnimeList()
    }

    override suspend fun searchForAnime(query: String, page: Int): List<Anime> {
        // This delay instead of using debounce
        delay(500L)
        return animeRemoteService.searchAnime(page, LIMIT, FILTER_ADULT_CONTENT, query)
            .animeList
            .toAnimeList()
    }

    override suspend fun saveAnimeAsFavourite(anime: Anime) {
        animeDatabaseSource.insertAnime(anime)
    }

    override fun isAnimeFavoriteById(id: Long): Flow<Boolean> {
        return animeDatabaseSource.isAnimeFavoriteById(id)
    }

    override suspend fun deleteAnime(animeId:Long) {
        withContext(Dispatchers.IO) { imageStorageHandler.deleteImage(animeId) }
        animeDatabaseSource.deleteAnime(animeId)
    }

    override fun getAllFavouriteAnimeList(): Flow<List<FavAnime>> {
        return animeDatabaseSource.getAllFavouriteAnimeList().map {
            it.map { anime ->
                supervisorScope {
                    async {
                        val imageBitmap =
                            imageStorageHandler.getImage(anime.id) ?: return@async null
                        FavAnime(
                            id = anime.id,
                            name = anime.name,
                            image = imageBitmap,
                            airingStatus = anime.airingStatus,
                            numberOfEpisodes = anime.numberOfEpisodes,
                            showType = anime.showType,
                            dateOfInsertion = anime.dateOfInsertion
                        )
                    }
                }
            }.mapNotNull { it.await() }
        }
    }
}