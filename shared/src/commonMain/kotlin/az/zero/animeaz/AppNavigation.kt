package az.zero.animeaz

import az.zero.animeaz.domain.model.Anime
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * *** Important setup steps Router library didn't mention ***
 *
 * 1- You have to add dependencies to the android gradle
 * 2- You have to add id("kotlin-parcelize") to common main plugins
 * 3- add @Parcelize annotation and implement Parcelable in the class you wanna pass to screens
 * */

@Parcelize
sealed class ScreenDestination : Parcelable {
    object HomeScreenDestination : ScreenDestination()
    object SearchScreenDestination : ScreenDestination()
    object FavoriteScreenDestination : ScreenDestination()
    data class DetailsScreenDestination(val anime: Anime) : ScreenDestination()
    object AuthDestination : ScreenDestination()
}