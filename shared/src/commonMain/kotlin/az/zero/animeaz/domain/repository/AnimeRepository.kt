package az.zero.animeaz.domain.repository

import az.zero.animeaz.domain.model.Anime

interface AnimeRepository {

    suspend fun getTopAnimeList(page: Int): List<Anime>
    suspend fun searchForAnime(query: String, page: Int): List<Anime>

}


