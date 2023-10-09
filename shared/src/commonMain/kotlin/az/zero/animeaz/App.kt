package az.zero.animeaz

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import az.zero.animeaz.ScreenDestination.AuthDestination
import az.zero.animeaz.ScreenDestination.DetailsScreenDestination
import az.zero.animeaz.ScreenDestination.FavoriteScreenDestination
import az.zero.animeaz.ScreenDestination.HomeScreenDestination
import az.zero.animeaz.ScreenDestination.SearchScreenDestination
import az.zero.animeaz.data.local.preferences.Preferences
import az.zero.animeaz.presentation.screens.auth.GalleryAuthScreen
import az.zero.animeaz.presentation.screens.details.DetailsScreen
import az.zero.animeaz.presentation.screens.favorite.FavoriteScreen
import az.zero.animeaz.presentation.screens.home.HomeScreen
import az.zero.animeaz.presentation.screens.search.SearchScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import io.github.xxfast.decompose.LocalComponentContext
import io.github.xxfast.decompose.router.content.RoutedContent
import io.github.xxfast.decompose.router.rememberRouter

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean,
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        Surface(
            color = Color.Black
        ) {
//            val hasBioAuth = Preferences.getBioAuthLock()
            val hasBioAuth = false
            val initialRoute = if (hasBioAuth) AuthDestination else HomeScreenDestination
            val router = rememberRouter(ScreenDestination::class, listOf(initialRoute))

            RoutedContent(
                router = router,
                animation = predictiveBackAnimation(
                    backHandler = LocalComponentContext.current.backHandler,
                    onBack = { router.pop() },
                    animation = stackAnimation(fade())
                ),
            ) { screen ->
                when (screen) {
                    HomeScreenDestination -> HomeScreen(
                        onAnimeClick = {
                            router.push(DetailsScreenDestination(it))
                        },
                        onSearchClick = { router.push(SearchScreenDestination) },
                        onFavListClick = { router.push(FavoriteScreenDestination) }
                    )

                    SearchScreenDestination -> SearchScreen(
                        onAnimeClick = {
                            router.push(DetailsScreenDestination(it))
                        },
                        onBackPressed = { router.pop() }
                    )

                    is DetailsScreenDestination -> DetailsScreen(screen.anime) {
                        router.pop()
                    }

                    AuthDestination -> GalleryAuthScreen {
                        router.replaceCurrent(HomeScreenDestination)
                    }

                    FavoriteScreenDestination -> FavoriteScreen()
                }
            }
        }

    }
}