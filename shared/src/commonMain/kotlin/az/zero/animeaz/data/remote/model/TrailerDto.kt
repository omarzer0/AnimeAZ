package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailerDto(
    @SerialName("embed_url")
    val embedUrl: String?,
    @SerialName("images")
    val images: ImagesDtoX?,
    @SerialName("url")
    val url: String?,
    @SerialName("youtube_id")
    val youtubeId: String?
)