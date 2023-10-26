package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap

actual class ImageStorageHandler {

    actual suspend fun saveImage(id: Long, bytes: ByteArray): String {
        return ""
    }

    actual suspend fun getImage(id: Long): ImageBitmap? {
        return null
    }

    actual suspend fun deleteImage(id: Long) {

    }
}