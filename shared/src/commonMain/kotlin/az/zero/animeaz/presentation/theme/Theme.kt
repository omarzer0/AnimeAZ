package az.zero.animeaz.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val darkMain = Color(0xFF00112A)
private val darkSurface = Color(0xFF001B3D)
val DarkColorScheme = darkColorScheme(
    primary = darkMain,
    onPrimary = Color.White,
    background = darkMain,
    onBackground = Color.White,
    surface = darkSurface,
    onSurface = Color.White
)

private val lightMain = Color(0xFFD6E3FF)
private val lightSurface = Color(0xFFECF0FF)
val LightColorScheme = lightColorScheme(
    primary = lightMain,
    onPrimary = Color.Black,
    background = lightMain,
    onBackground = Color.Black,
    surface = lightSurface,
    onSurface = Color.Black
)