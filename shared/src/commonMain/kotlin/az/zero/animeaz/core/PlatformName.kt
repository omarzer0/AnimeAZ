package az.zero.animeaz.core

import androidx.compose.runtime.Stable

@Stable
enum class PlatformName {
    ANDROID,
    IOS,
    DESKTOP

}

@Stable
expect fun getPlatformName(): PlatformName

