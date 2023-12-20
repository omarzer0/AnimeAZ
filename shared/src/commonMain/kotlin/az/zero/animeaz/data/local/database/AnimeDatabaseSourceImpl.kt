package az.zero.animeaz.data.local.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import az.zero.animeaz.data.util.DateTimeUtil
import az.zero.animeaz.database.AppDatabase
import az.zero.animeaz.domain.database.AnimeDatabaseSource
import az.zero.animeaz.domain.model.Anime
import database.AnimeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeDatabaseSourceImpl(
    private val db: AppDatabase,
    private val dateTimeUtil: DateTimeUtil
) : AnimeDatabaseSource {
    private val queries = db.animeQueries

    override fun getAllFavouriteAnimeList(): Flow<List<AnimeEntity>> {
        return queries.getAllAnimes().asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun insertAnime(anime: Anime) {
        queries.insertAnime(
            id = anime.id,
            name = anime.name,
            image = anime.image,
            airingStatus = if (anime.airingStatus) 1 else 0,
            numberOfEpisodes = anime.numberOfEpisodes,
            showType = anime.showType,
            dateOfInsertion = dateTimeUtil.now()
        )
    }

    override fun isAnimeFavoriteByIdFlow(id: Long): Flow<Boolean> {
        return queries.isAnimeFavoriteById(id).asFlow()
            .map {
                val exist = it.executeAsOneOrNull()
                exist != null && exist > 0
            }
    }

    override fun isAnimeFavoriteById(id: Long): Boolean {
        val exist = queries.isAnimeFavoriteById(id).executeAsOneOrNull()
        return exist != null && exist > 0
    }

    override suspend fun deleteAnime(id: Long) {
        queries.deleteAnime(id)
    }

}