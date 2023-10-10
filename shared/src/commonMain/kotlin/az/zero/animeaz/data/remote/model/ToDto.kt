package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToDto(
    @SerialName("day")
    val day: Int?,
    @SerialName("month")
    val month: Int?,
    @SerialName("year")
    val year: Int?
)