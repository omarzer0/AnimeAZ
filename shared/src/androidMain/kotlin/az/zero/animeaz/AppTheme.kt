package az.zero.animeaz

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import az.zero.animeaz.presentation.theme.DarkColorScheme
import az.zero.animeaz.presentation.theme.LightColorScheme
import az.zero.animeaz.presentation.theme.Typography

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    /*
    * To change the status bar color as the android system does automatically we need access to
    * the window of the activity and change the "statusBarColor" value
    *
    * Also we need to react to status bar color changes and change the icon color depending on if
    * it is light or dark theme by changing isAppearanceLightStatusBars
    * */
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !colorScheme.primary.isColorDark()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !colorScheme.primary.isColorDark()

        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}

@Stable
fun Color.isColorDark(): Boolean {
    val darkness: Double =
        1 - (0.299 * this.red + 0.587 * this.green + 0.114 * this.blue / 255)
    return darkness >= 0.5
}