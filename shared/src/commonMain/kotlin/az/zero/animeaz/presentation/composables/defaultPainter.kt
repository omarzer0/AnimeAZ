package az.zero.animeaz.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import az.zero.animeaz.SharedRes
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun rememberDefaultPainter(url: String): Painter {
    return rememberImagePainter(
        url,
        placeholderPainter = {
            painterResource(SharedRes.images.placeholder)
        },
        errorPainter = {
            painterResource(SharedRes.images.no_image)
        }
    )
}