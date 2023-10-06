package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap
import dev.icerock.moko.media.Bitmap

expect class ImageStorageHandler {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(fileName: String): ImageBitmap?
    suspend fun deleteImage(fileName: String)

}