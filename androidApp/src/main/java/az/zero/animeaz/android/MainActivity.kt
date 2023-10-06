package az.zero.animeaz.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.FragmentActivity
import az.zero.animeaz.App
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import io.github.xxfast.decompose.LocalComponentContext

class MainActivity : FragmentActivity() {
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