package az.zero.animeaz.data.local.database.mapper

import az.zero.animeaz.domain.model.Anime
import database.AnimeEntity

fun AnimeEntity.toAnime(): Anime {
    // FIXME: the sql delight plugin should return int when the type is INT or INTEGER but instead it returns Long
    return Anime(
        id = id,
        name = name,
        image = image,
        cover = cover,
        score = score.toFloat(),
        reviewCount = reviewCount.toInt(),
        rank = rank.toInt(),
        popularity = popularity.toInt(),
        airingStatus = airingStatus,
        description = description,
        season = season,
        year = year,
        numberOfEpisodes = numberOfEpisodes.toInt(),
        showType = showType,
        genres = genres
    )
}