package az.zero.animeaz.domain.repository

import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.model.FavAnime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getTopAnimeList(page: Int): List<Anime>
    suspend fun searchForAnime(query: String, page: Int): List<Anime>
    suspend fun saveAnimeAsFavourite(anime: Anime)
    suspend fun deleteAnime(animeId: Long)
    fun isAnimeFavoriteByIdFlow(id: Long): Flow<Boolean>
    fun getAllFavouriteAnimeList(): Flow<List<FavAnime>>
    fun isAnimeFavoriteById(id: Long): Boolean
}


