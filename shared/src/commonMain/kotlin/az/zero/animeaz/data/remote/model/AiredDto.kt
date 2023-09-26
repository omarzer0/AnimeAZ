package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiredDto(
    @SerialName("from")
    val from: String?,
    @SerialName("prop")
    val prop: PropDto?,
    @SerialName("string")
    val string: String?,
    @SerialName("to")
    val to: String?
)