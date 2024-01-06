package az.zero.animeaz.presentation.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
actual fun isInDarkTheme(): Boolean {
    return isSystemInDarkTheme()
}