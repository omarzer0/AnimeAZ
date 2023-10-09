package az.zero.animeaz.data.local.database.mapper

import az.zero.animeaz.domain.model.Anime
import database.AnimeEntity

fun AnimeEntity.toAnime(): Anime {
    // FIXME: the sql delight plugin should return int when the type is INT or
    //  INTEGER but instead it returns Long so I will change Int to long for now 🙄🙄🙄🙄🙄🙄🙄🙄
    return Anime(
        id = id,
        name = name,
        image = image,
        cover = cover,
        score = score.toFloat(),
        reviewCount = reviewCount,
        rank = rank,
        popularity = popularity,
        airingStatus = airingStatus,
        description = description,
        season = season,
        year = year,
        numberOfEpisodes = numberOfEpisodes,
        showType = showType,
        genres = genres
    )
}