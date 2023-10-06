package az.zero.animeaz.domain.database

import az.zero.animeaz.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeDatabaseSource {

    fun getAllFavouriteAnimeList(): Flow<List<Anime>>

    suspend fun insertAnime(anime: Anime)

    suspend fun deleteAnime(id: Int)
}
