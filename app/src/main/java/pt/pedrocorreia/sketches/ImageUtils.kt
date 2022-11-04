package pt.pedrocorreia.sketches

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.min

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

fun setPic(view: View, path: String) {
    val targetW = view.width
    val targetH = view.height
    if (targetH < 1 || targetW < 1)
        return
    val bmpOptions = BitmapFactory.Options()
    bmpOptions.inJustDecodeBounds = true // apenas ir buscar os limites da imagem
    BitmapFactory.decodeFile(path, bmpOptions)
    val photoW = bmpOptions.outWidth
    val photoH = bmpOptions.outHeight
    val scale = max(1, min(photoW / targetW, photoH / targetH))
    bmpOptions.inSampleSize = scale // meter a escala nas options
    bmpOptions.inJustDecodeBounds = false // tirar ir buscar apenas os limites
    val bitmap = BitmapFactory.decodeFile(path, bmpOptions) // agora lê o bitmap com a escala definida
    when (view) {
        is ImageView -> (view).setImageBitmap(bitmap)
        //else -> view.background = bitmap.toDrawable(view.resources)
        else -> view.background = BitmapDrawable(view.resources, bitmap)
    }
}

fun saveSketchLocation(context : Context, prefix : String = "Sketch", extension : String = ".png") : String {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath!!+"/${prefix}$extension")
    file.createNewFile()
    return file.absolutePath
}

fun saveSketch(savePath : String, bitmap : Bitmap) : Boolean{
    return bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(File(savePath)))
}