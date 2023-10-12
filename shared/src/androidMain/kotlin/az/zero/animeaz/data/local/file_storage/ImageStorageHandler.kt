package az.zero.animeaz.data.local.file_storage

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

actual class ImageStorageHandler(
    private val context: Context
) {
    actual suspend fun saveImage(id:Long, bytes: ByteArray): String {
        return withContext(Dispatchers.IO) {
            val fileName = "image_saved_$id.jpg"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                outputStream.write(bytes)
            }
            fileName
        }
    }

    actual suspend fun getImage(id: Long): ImageBitmap? {
        return withContext(Dispatchers.IO) {
            val fileName = "image_saved_$id.jpg"
            val bytes = context.openFileInput(fileName).use { inputStream ->
                inputStream.readBytes()
            }
             BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
         }
    }

    actual suspend fun deleteImage(id: Long) {
        return withContext(Dispatchers.IO) {
            val fileName = "image_saved_$id.jpg"
            context.deleteFile(fileName)
        }
    }
}