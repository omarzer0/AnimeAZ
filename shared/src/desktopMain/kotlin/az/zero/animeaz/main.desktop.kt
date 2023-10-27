package az.zero.animeaz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import az.zero.animeaz.di.PlatformModule
import az.zero.animeaz.di.sharedModule
import com.arkivanov.decompose.DefaultComponentContext
import org.koin.core.context.startKoin
import java.io.File

@Composable
fun MainView(rootComponentContext: DefaultComponentContext) {
    App(darkTheme = false, dynamicColor = false, rootComponentContext = rootComponentContext)
}