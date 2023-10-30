package az.zero.animeaz.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Stable
@Composable
fun Modifier.mirror(
    direction: LayoutDirection = LayoutDirection.Rtl,
): Modifier {
    return if (LocalLayoutDirection.current == direction) this.scale(scaleX = -1f, scaleY = 1f)
    else this
}
