package az.zero.animeaz.data.remote.model


import az.zero.animeaz.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("mal_id")
    val id: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?
)

fun GenreDto.toGenre(): Genre? {
    return Genre(
        id = this.id ?: return null,
        name = this.name ?: return null,
        type = this.type ?: return null,
        url = this.url ?: ""
    )
}

fun List<GenreDto?>?.toGenreList(): List<Genre> {
    return this?.mapNotNull { it?.toGenre() } ?: emptyList()
}