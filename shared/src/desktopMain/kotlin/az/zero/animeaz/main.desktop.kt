package az.zero.animeaz

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.DefaultComponentContext

@Composable
fun MainView(rootComponentContext: DefaultComponentContext) {
    App(dynamicColor = false, rootComponentContext = rootComponentContext)
}