package az.zero.animeaz.data.local.database

import az.zero.animeaz.data.local.database.mapper.toAnime
import az.zero.animeaz.database.AppDatabase
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.domain.model.Anime
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeDatabaseSourceImpl(db: AppDatabase) : AnimeDatabaseSource {
    private val queries = db.animeQueries
    override fun getAllFavouriteAnimeList(): Flow<List<Anime>> {
        return queries.getAllAnimes().asFlow()
            .mapToList()
            .map {
                it.map { it.toAnime() }
            }
    }

    override suspend fun insertAnime(anime: Anime) {
        queries.insertAnime(
            id = anime.id,
            name = anime.name,
            image = anime.image,
            cover = anime.cover,
            score = anime.score.toDouble(),
            reviewCount = anime.reviewCount,
            rank = anime.reviewCount,
            popularity = anime.popularity,
            airingStatus = anime.airingStatus,
            description = anime.description,
            season = anime.season,
            year = anime.year,
            numberOfEpisodes = anime.numberOfEpisodes,
            showType = anime.showType,
            genres = anime.genres
        )
    }

    override fun isAnimeFavoriteById(id: Long): Flow<Boolean> {
        return queries.isAnimeFavoriteById(id).asFlow()
            .map {
                val exist = it.executeAsOneOrNull()
                exist != null && exist > 0
            }
    }

    override suspend fun deleteAnime(id: Long) {
        queries.deleteAnime(id)
    }

}