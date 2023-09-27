package az.zero.animeaz.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto(
    @SerialName("current_page")
    val currentPage: Int?,
    @SerialName("has_next_page")
    val hasNextPage: Boolean?,
    @SerialName("items")
    val items: ItemsDto?,
    @SerialName("last_visible_page")
    val lastVisiblePage: Int?
)