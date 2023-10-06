package az.zero.animeaz.data.local.file_storage

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dev.icerock.moko.media.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

actual class ImageStorageHandler(
    private val context: Context
) {
    actual suspend fun saveImage(bytes: ByteArray): String {
        return withContext(Dispatchers.IO) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                outputStream.write(bytes)
            }
            fileName
        }
    }

    actual suspend fun getImage(fileName: String): ImageBitmap? {
        return withContext(Dispatchers.IO) {
            val bytes = context.openFileInput(fileName).use { inputStream ->
                inputStream.readBytes()
            }
             BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
         }
    }

    actual suspend fun deleteImage(fileName: String) {
        return withContext(Dispatchers.IO) {
            context.deleteFile(fileName)
        }
    }
}