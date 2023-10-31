package az.zero.animeaz

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import az.zero.animeaz.presentation.theme.DarkColorScheme
import az.zero.animeaz.presentation.theme.LightColorScheme
import az.zero.animeaz.presentation.theme.Typography

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}

