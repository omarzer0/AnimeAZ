package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BroadcastDto(
    @SerialName("day")
    val day: String?,
    @SerialName("string")
    val string: String?,
    @SerialName("time")
    val time: String?,
    @SerialName("timezone")
    val timezone: String?
)