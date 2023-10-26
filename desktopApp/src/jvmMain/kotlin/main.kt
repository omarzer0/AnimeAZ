import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import az.zero.animeaz.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainView()
    }
}