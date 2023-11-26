import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import az.zero.animeaz.MainView
import az.zero.animeaz.di.PlatformModule
import az.zero.animeaz.di.sharedModule
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.koin.core.context.startKoin
import java.awt.Dimension


fun main() {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    // FIXME I haven't figure out how to setup koin for Desktop correctly but it works
    startKoin {
        modules(PlatformModule().module + sharedModule)
    }

    application {
        val windowState: WindowState = rememberWindowState()

        Window(
            title = "AnimeAZ",
            state = windowState,
            onCloseRequest = { exitApplication() }
        ) {
            window.minimumSize = Dimension(350, 550)
            MainView(rootComponentContext = rootComponentContext)
        }

    }
}