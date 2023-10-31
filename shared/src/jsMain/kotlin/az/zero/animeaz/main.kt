package az.zero.animeaz

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle)

    onWasmReady {
        CanvasBasedWindow("Multiplatform App") {
            App(
                darkTheme = false,
                dynamicColor = false,
                rootComponentContext = rootComponentContext
            )
        }
    }
}
