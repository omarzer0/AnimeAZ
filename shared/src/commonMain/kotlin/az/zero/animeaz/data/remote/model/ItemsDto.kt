package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemsDto(
    @SerialName("count")
    val count: Int?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("total")
    val total: Int?
)