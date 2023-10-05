package az.zero.animeaz.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Anime(
    val id: Long,
    val englishName: String,
    val image: String,
    val cover: String,
    val score: Float,
    val reviewCount: Int,
    val rank: Int,
    val popularity: Int,
    val airingStatus: Boolean,
    val description:String,
    val genres:List<Genre>
) : Parcelable

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    val type: String,
    val url: String
): Parcelable