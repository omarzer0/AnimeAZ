package az.zero.animeaz.data.remote

import az.zero.animeaz.data.remote.model.TopAnimeResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AnimeRemoteServiceImpl(
    private val client: HttpClient
) : AnimeRemoteService {
    override suspend fun getTopAnimeList(
        page: Int,
        limit: Int,
        filterAdultContent: Boolean
    ): TopAnimeResponseDto {
        println("Custom getTopAnimeList")
        return client.get("top/anime") {
            url {
                parameters.append("page", page.toString())
                parameters.append("limit", limit.toString())
                parameters.append("sfw", filterAdultContent.toString())
            }
        }.body()
    }

    override suspend fun searchAnime(
        page: Int,
        limit: Int,
        filterAdultContent: Boolean,
        query: String
    ): TopAnimeResponseDto {
        return client.get("anime") {
            url {
                parameters.append("page", page.toString())
                parameters.append("limit", limit.toString())
                parameters.append("sfw", filterAdultContent.toString())
                parameters.append("q", query)
            }
        }.body()
    }


}