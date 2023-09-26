package az.zero.animeaz.data.remote.model


import az.zero.animeaz.domain.model.Anime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeResponseDto(
    @SerialName("data")
    val animeList: List<AnimeDto?>?,
    @SerialName("pagination")
    val pagination: PaginationDto?
)

fun TopAnimeResponseDto.mapToAnimeList(): List<Anime> {
    return this.animeList?.filterNotNull()?.map {
        Anime(
            englishName = it.titleEnglish ?: "",
            image = it.images?.jpg?.imageUrl ?: "",
            score = it.score?.toFloat() ?: 0.0f,
            airingStatus = it.airing ?: false
        )
    } ?: emptyList()
}