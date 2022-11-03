package pt.pedrocorreia.sketches

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun getTempFilename(context: Context,
prefix: String = "image", extension : String = ".img") : String =
    File.createTempFile(
        prefix, extension,
        context.externalCacheDir
    ).absolutePath

fun createFileFromUri(
    context: Context,
    uri : Uri,
    filename : String = getTempFilename(context)
) : String {
    FileOutputStream(filename).use { outputStream ->
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return filename
}