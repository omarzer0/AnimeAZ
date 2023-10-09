package az.zero.animeaz.domain.repository

import az.zero.animeaz.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    suspend fun getTopAnimeList(page: Int): List<Anime>
    suspend fun searchForAnime(query: String, page: Int): List<Anime>

    suspend fun saveAnimeAsFavourite(anime: Anime)
    fun isAnimeFavoriteById(id: Long): Flow<Boolean>
    suspend fun deleteAnime(id: Long)
    fun getAllFavouriteAnimeList(): Flow<List<Anime>>
}


