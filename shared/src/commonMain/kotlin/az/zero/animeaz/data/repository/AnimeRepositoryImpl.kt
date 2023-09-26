package az.zero.animeaz.data.repository

import az.zero.animeaz.data.remote.AnimeRemoteService
import az.zero.animeaz.data.remote.model.mapToAnimeList
import az.zero.animeaz.data.remote.util.ResponseState
import az.zero.animeaz.data.remote.util.networkCall
import az.zero.animeaz.domain.model.Anime
import az.zero.animeaz.domain.repository.AnimeRepository
import az.zero.animeaz.util.Constants.FILTER_ADULT_CONTENT
import az.zero.animeaz.util.Constants.LIMIT
import kotlinx.coroutines.flow.Flow

class AnimeRepositoryImpl(
    private val animeRemoteService: AnimeRemoteService
) : AnimeRepository {
    override fun getTopAnimeList(page: Int): Flow<ResponseState<List<Anime>>> {
        return networkCall(
            request = { animeRemoteService.getTopAnimeList(page, LIMIT, FILTER_ADULT_CONTENT) },
            mapper = { it.mapToAnimeList() }
        )
    }


}