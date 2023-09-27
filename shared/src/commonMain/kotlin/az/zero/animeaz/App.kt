package az.zero.animeaz

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import az.zero.animeaz.ScreenDestination.SearchScreenDestination
import az.zero.animeaz.presentation.screens.home.HomeScreen
import az.zero.animeaz.presentation.screens.search.SearchScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
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
            val initialRoute = ScreenDestination.HomeScreenDestination
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
                    ScreenDestination.HomeScreenDestination -> HomeScreen(
                        onAnimeClick = {},
                        onSearchClick = { router.push(SearchScreenDestination) }
                    )

                    SearchScreenDestination -> SearchScreen(
                        onAnimeClick = {},
                        onBackPressed = { router.pop() }
                    )
                }
            }
        }

    }
}