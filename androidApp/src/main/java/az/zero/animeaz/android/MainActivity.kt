package az.zero.animeaz.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import az.zero.animeaz.App
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import io.github.xxfast.decompose.LocalComponentContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponentContext: DefaultComponentContext = defaultComponentContext()
        setContent {
            CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
                App(
                    darkTheme = isSystemInDarkTheme(),
                    dynamicColor = true,
                )
            }
        }
    }
}