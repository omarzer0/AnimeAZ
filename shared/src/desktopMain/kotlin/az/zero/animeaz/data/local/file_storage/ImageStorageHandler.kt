package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap
import com.seiko.imageloader.asImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import java.io.File

actual class ImageStorageHandler {

    private val rootFilePath = System.getProperty("user.dir")
    private val imageDir = "/src/desktopMain/cachedImages"
    private val path = rootFilePath.plus(imageDir)

    private fun getImagePathById(id: Long): String {
        createDirIfNotExist()
        return "${path}/image_saved_$id.jpg"
    }

    actual suspend fun saveImage(id: Long, bytes: ByteArray): String {
        val filePath = getImagePathById(id)
        val file = File(filePath)
        file.writeBytes(bytes)
        return file.absolutePath
    }

    actual suspend fun getImage(id: Long): ImageBitmap? {
        val filePath = getImagePathById(id)
        val bytes = File(filePath).readBytes()
        return Bitmap.makeFromImage(Image.makeFromEncoded(bytes)).asImageBitmap()
    }

    actual suspend fun deleteImage(id: Long): Boolean {
        val filePath = getImagePathById(id)
        return File(filePath).delete()
    }

    private fun createDirIfNotExist(){
        val file = File(path)
        if (!file.exists()) file.mkdir()
    }
}