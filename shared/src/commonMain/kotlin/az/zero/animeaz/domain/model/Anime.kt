package az.zero.animeaz.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Anime(
    val id: Long,
    val englishName: String,
    val image: String,
    val cover:String,
    val score: Float,
    val airingStatus: Boolean,
) : Parcelable