import androidx.compose.ui.window.ComposeUIViewController
import az.zero.animeaz.App
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun MainViewController() = ComposeUIViewController {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    App(
        dynamicColor = false,
        rootComponentContext = rootComponentContext
    )

}