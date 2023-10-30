package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap
import com.seiko.imageloader.asImageBitmap
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.getBytes
import platform.Foundation.stringByAppendingPathComponent
import platform.Foundation.writeToFile

actual class ImageStorageHandler {

    private val fileManager = NSFileManager.defaultManager
    private val documentDirectory = NSSearchPathForDirectoriesInDomains(
        directory = NSDocumentDirectory,
        domainMask = NSUserDomainMask,
        expandTilde = true
    ).first() as NSString

    actual suspend fun saveImage(id: Long, bytes: ByteArray): String {
        return withContext(Dispatchers.Default) {
            val fileName = "image_saved_$id.jpg"
            val fullPath = documentDirectory.stringByAppendingPathComponent(fileName)

            val data = bytes.usePinned {
                NSData.create(
                    bytes = it.addressOf(0),
                    length = bytes.size.toULong()
                )
            }
            data.writeToFile(
                path = fullPath,
                atomically = true
            )
            fullPath
        }
    }

    actual suspend fun getImage(id: Long): ImageBitmap? {
        return withContext(Dispatchers.Default) {
            val fileName = "image_saved_$id.jpg"
            val fullPath = documentDirectory.stringByAppendingPathComponent(fileName)

            memScoped {
                NSData.dataWithContentsOfFile(fullPath)?.let { bytes ->
                    val array = ByteArray(bytes.length.toInt())
                    bytes.getBytes(array.refTo(0).getPointer(this), bytes.length)
                    return@withContext Bitmap.makeFromImage(Image.makeFromEncoded(array))
                        .asImageBitmap()
                }
            }
            return@withContext null
        }
    }

    actual suspend fun deleteImage(id: Long): Boolean {
        val fileName = "image_saved_$id.jpg"
        val fullPath = documentDirectory.stringByAppendingPathComponent(fileName)
        return fileManager.removeItemAtPath(fullPath, null)
    }
}