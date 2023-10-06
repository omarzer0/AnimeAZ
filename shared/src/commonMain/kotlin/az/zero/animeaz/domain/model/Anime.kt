package az.zero.animeaz.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Anime(
    val id: Long,
    val name: String,
    val image: String,
    val cover: String,
    val score: Float,
    val reviewCount: Int,
    val rank: Int,
    val popularity: Int,
    val airingStatus: Boolean,
    val description: String,
    val season: String,
    val year: String,
    val numberOfEpisodes: Int,
    val showType: String,
    val genres: List<Genre>,
) : Parcelable {
    val seasonWithYear get() = "${this.season} ${this.year}"

    val typeOfShowWithNumberOfEpisodes get() = "${this.showType} | ${this.numberOfEpisodes} Episode${if (this.numberOfEpisodes > 1) "s" else ""}"

}

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    val type: String,
    val url: String
) : Parcelable


//@Parcelize
//data class Broadcast(
//    val day: String,
//    val string: String,
//    val time: String,
//    val timezone: String?
//):Parcelable