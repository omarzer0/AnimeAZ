package az.zero.animeaz.domain.database

import az.zero.animeaz.domain.model.Anime
import database.AnimeEntity
import kotlinx.coroutines.flow.Flow

interface AnimeDatabaseSource {
    fun getAllFavouriteAnimeList(): Flow<List<AnimeEntity>>
    fun isAnimeFavoriteById(id: Long): Flow<Boolean>
    suspend fun insertAnime(anime: Anime)
    suspend fun deleteAnime(id: Long)
}
