package az.zero.animeaz.data.local.file_storage

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import java.io.File
import java.io.FileOutputStream

actual class ImageStorageHandler {

    actual suspend fun saveImage(id: Long, bytes: ByteArray): String {
//        val osName = System.getProperty("os.name").lowercase()
//        val userHome = System.getProperty("user.home")
//        val rootPath = File(System.getProperty("compose.application.resources.dir")).absolutePath
//
//        println("rootPath $rootPath")

//        val fileName = "/Users/omaradel/Desktop/image_saved_$id.jpg"

        val rootFile = File(System.getProperty("compose.application.resources.dir"))
        val rootPath = rootFile.canonicalPath
        val fileName = "${rootPath}/image_saved_$id.jpg"


        println("rootPath fileName $fileName")
        val file = File(fileName)
        file.createNewFile()
        file.writeBytes(bytes)
        println("rootPath writeBytes ${file.absolutePath}")

        return file.absolutePath

    }

    actual suspend fun getImage(id: Long): ImageBitmap? {
//        val  x= Image.makeFromEncoded(bytes)
        return null
    }

    actual suspend fun deleteImage(id: Long) {

    }
}