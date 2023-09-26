package az.zero.animeaz.domain.repository

import az.zero.animeaz.data.remote.util.ResponseState
import az.zero.animeaz.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

     fun getTopAnimeList(
        page: Int,
    ): Flow<ResponseState<List<Anime>>>


}


