package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDto(
    @SerialName("jpg")
    val jpg: JpgDto?,
    @SerialName("webp")
    val webp: WebpDto?
)