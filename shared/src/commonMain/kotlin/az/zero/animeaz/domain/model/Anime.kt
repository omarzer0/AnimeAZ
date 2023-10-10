package az.zero.animeaz.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Anime(
    val id: Long,
    val name: String,
    val image: String,
    val cover: String,
    val score: Float,
    val reviewCount: Long,
    val rank: Long,
    val popularity: Long,
    val airingStatus: Boolean,
    val description: String,
    val season: String,
    val year: String,
    val numberOfEpisodes: Long,
    val showType: String,
    val genres: List<Genre>,
) : Parcelable {
    val seasonWithYear get() = "${this.season} ${this.year}"

    val typeOfShowWithNumberOfEpisodes get() = "${this.showType} | ${this.numberOfEpisodes} Episode${if (this.numberOfEpisodes > 1) "s" else ""}"

}

@Serializable
@Parcelize
data class Genre(
    val id: Long,
    val name: String,
    val type: String,
    val url: String
) : Parcelable