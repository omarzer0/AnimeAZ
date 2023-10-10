package az.zero.animeaz.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class FavAnime(
    val id: Long,
    val name: String,
    val image: ImageBitmap,
    val airingStatus: Boolean,
    val numberOfEpisodes: Long,
    val showType: String,
    val dateOfInsertion:Long
){
    val typeOfShowWithNumberOfEpisodes get() = "${this.showType} | ${this.numberOfEpisodes} Episode${if (this.numberOfEpisodes > 1) "s" else ""}"
}