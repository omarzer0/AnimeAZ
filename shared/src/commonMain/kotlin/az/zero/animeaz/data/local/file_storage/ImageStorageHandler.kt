package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap

expect class ImageStorageHandler {
    suspend fun saveImage(id: Long, bytes: ByteArray): String
    suspend fun getImage(id: Long): ImageBitmap?
    suspend fun deleteImage(id: Long): Boolean

}