package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropDto(
    @SerialName("from")
    val from: FromDto?,
    @SerialName("to")
    val to: ToDto?
)