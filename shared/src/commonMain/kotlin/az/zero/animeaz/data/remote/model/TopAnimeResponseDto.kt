package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeResponseDto(
    @SerialName("data")
    val animeList: List<AnimeDto?>?,
    @SerialName("pagination")
    val pagination: PaginationDto?
)