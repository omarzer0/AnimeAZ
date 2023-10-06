package az.zero.animeaz.data.repository

import az.zero.animeaz.data.remote.AnimeRemoteService
import az.zero.animeaz.data.remote.model.toAnimeList
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.animeaz.util.Constants.FILTER_ADULT_CONTENT
import az.zero.animeaz.util.Constants.LIMIT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class AnimeRepositoryImpl(
    private val animeRemoteService: AnimeRemoteService,
    private val animeDatabaseSource: AnimeDatabaseSource
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

    override suspend fun deleteAnime(id: Int) {
        animeDatabaseSource.deleteAnime(id)
    }

    override suspend fun getAllFavouriteAnimeList(): Flow<List<Anime>> {
        return animeDatabaseSource.getAllFavouriteAnimeList()
    }


}