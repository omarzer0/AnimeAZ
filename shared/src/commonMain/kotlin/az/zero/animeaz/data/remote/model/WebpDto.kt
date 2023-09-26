package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebpDto(
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("large_image_url")
    val largeImageUrl: String?,
    @SerialName("small_image_url")
    val smallImageUrl: String?
)