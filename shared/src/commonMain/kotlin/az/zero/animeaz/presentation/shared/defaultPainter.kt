package az.zero.animeaz.presentation.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import az.zero.animeaz.SharedRes
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun rememberDefaultPainter(url: String): Painter {
    // FIXME **IMPORTANT** for some reason painterResource() from moko
    //  library sometimes doesn't work for the IOS
    //  try to solve it as soon as possible
    //  maybe it is a problem of coping resources to ios part
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