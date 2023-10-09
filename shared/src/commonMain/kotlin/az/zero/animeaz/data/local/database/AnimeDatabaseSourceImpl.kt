package az.zero.animeaz.data.local.database

import az.zero.animeaz.database.AppDatabase
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.util.DateTimeUtil
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.AnimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeDatabaseSourceImpl(
    private val db: AppDatabase,
    private val dateTimeUtil: DateTimeUtil
) : AnimeDatabaseSource {
    private val queries = db.animeQueries

    override fun getAllFavouriteAnimeList(): Flow<List<AnimeEntity>> {
        return queries.getAllAnimes().asFlow()
            .mapToList()
    }

    override suspend fun insertAnime(anime: Anime) {
        queries.insertAnime(
            id = anime.id,
            name = anime.name,
            image = anime.image,
            airingStatus = anime.airingStatus,
            numberOfEpisodes = anime.numberOfEpisodes,
            showType = anime.showType,
            dateOfInsertion = dateTimeUtil.now()
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