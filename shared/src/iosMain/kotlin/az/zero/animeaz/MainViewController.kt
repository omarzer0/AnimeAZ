package az.zero.animeaz

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.xxfast.decompose.LocalComponentContext
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    val lifecycle = LifecycleRegistry()
    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

    CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
        App(
            darkTheme = isDarkTheme,
            dynamicColor = false
        )
    }

}