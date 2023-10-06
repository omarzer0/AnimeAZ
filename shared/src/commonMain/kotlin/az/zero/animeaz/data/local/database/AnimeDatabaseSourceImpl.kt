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
        // FIXME: the sql delight plugin should return int when the type is INT or INTEGER but instead it returns Long
        queries.insertAnime(
            id = anime.id,
            name = anime.name,
            image = anime.image,
            cover = anime.cover,
            score = anime.score.toDouble(),
            reviewCount = anime.reviewCount.toLong(),
            rank = anime.reviewCount.toLong(),
            popularity = anime.popularity.toLong(),
            airingStatus = anime.airingStatus,
            description = anime.description,
            season = anime.season,
            year = anime.year,
            numberOfEpisodes = anime.numberOfEpisodes.toLong(),
            showType = anime.showType,
            genres = anime.genres
        )
    }

    override suspend fun deleteAnime(id: Int) {
        // FIXME:  the sql delight plugin .... 🙄🙄🙄🙄🙄🙄🙄🙄 I will not change the Anime id to long 🙄🙄
        queries.deleteAnime(id.toLong())
    }

}