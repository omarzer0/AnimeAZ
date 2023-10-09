package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap

expect class ImageStorageHandler {
    suspend fun saveImage(bytes: ByteArray): String
    suspend fun getImage(fileName: String): ImageBitmap?
    suspend fun deleteImage(fileName: String)

}