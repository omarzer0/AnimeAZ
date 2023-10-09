package az.zero.animeaz.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class FavAnime(
    val id: Long,
    val name: String,
    val image: ImageBitmap,
    val score: Float,
    val reviewCount: Long,
    val rank: Long,
    val popularity: Long,
    val airingStatus: Boolean,
    val description: String,
    val season: String,
    val year: String,
    val numberOfEpisodes: Long,
    val showType: String
)