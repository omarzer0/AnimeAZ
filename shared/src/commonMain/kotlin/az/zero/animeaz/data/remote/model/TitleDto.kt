package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleDto(
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?
)