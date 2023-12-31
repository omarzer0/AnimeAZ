package az.zero.animeaz.data.remote

import az.zero.animeaz.data.remote.model.TopAnimeResponseDto

interface AnimeRemoteService {

    suspend fun getTopAnimeList(
        page: Int,
        limit: Int,
        filterAdultContent: Boolean
    ): TopAnimeResponseDto

    suspend fun searchAnime(
        page: Int,
        limit: Int,
        filterAdultContent: Boolean,
        query: String
    ): TopAnimeResponseDto

}