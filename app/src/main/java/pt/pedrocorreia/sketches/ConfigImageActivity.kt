package pt.pedrocorreia.sketches

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import pt.pedrocorreia.sketches.databinding.ActivityConfigImageBinding
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max
import kotlin.math.min

class ConfigImageActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val MODE_KEY = "mode"

        private var imagePath : String? = null

        fun getGalleryIntent(context: Context) : Intent{
            val intent = Intent(context, ConfigImageActivity::class.java)
            intent.putExtra(MODE_KEY, GALLERY)
            return intent
        }

        fun getCameraIntent(context: Context) : Intent{
            val intent = Intent(context, ConfigImageActivity::class.java)
            intent.putExtra(MODE_KEY, CAMERA)
            return intent
        }
    }

    private var mode = GALLERY

    lateinit var binding: ActivityConfigImageBinding
    private var permissionsGranted = false
        set(value){
            field = value
            binding.btnImage.isEnabled = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mode = intent.getIntExtra(MODE_KEY, GALLERY)

        when(mode){
            GALLERY -> binding.btnImage.apply{
                text = getString(R.string.btn_choose_image)
                setOnClickListener { chooseImage() }
            }
            CAMERA -> binding.btnImage.apply{
                text = getString(R.string.btn_take_photo)
                setOnClickListener { takePhoto() }
            }
            else -> finish()
        }

        verifyPermissions()
        updatePreview()
    }

//    private fun verifyPermissions(){
//        if ( ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        )  {
//            permissionsGranted = false
//            ActivityCompat.requestPermissions(this,
//                arrayOf(
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                PERMISSION_REQUEST_CODE
//            )
//        } else {
//            permissionsGranted = true
//        }
//    }

    private fun verifyPermissions() {
        requestPermissionsLauncher.launch(
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grantResults -> // Map<String,Boolean>
        permissionsGranted = grantResults.values.all { it }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED}
//            return
//        }
//        super.onRequestPermissionsResult(
//            requestCode, permissions, grantResults)
//    }

    private fun chooseImage(){
//        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForGalleryResult.launch(intent)
    }

    private var startActivityForGalleryResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultIntent = result.data
            resultIntent?.data?.let { uri ->
                imagePath = createFileFromUri(this, uri)
                    updatePreview()
            }
        }
    }

    private fun takePhoto(){
        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
    }

    private fun updatePreview(){
        if(imagePath != null){
            setPic(binding.frPreview, imagePath!!)
        }
    }

    private fun setPic(view: View, path: String) {
        val targetW = view.width
        val targetH = view.height
        if (targetH < 1 || targetW < 1)
            return
        val bmpOptions = BitmapFactory.Options()
        bmpOptions.inJustDecodeBounds = true // apenas ir buscar os limites da imagem
        BitmapFactory.decodeFile(path, bmpOptions)
        val photoW = bmpOptions.outWidth
        val photoH = bmpOptions.outHeight
        val scale = max(1,min(photoW / targetW, photoH / targetH))
        bmpOptions.inSampleSize = scale // meter a escala nas options
        bmpOptions.inJustDecodeBounds = false // tirar ir buscar apenas os limites
        val bitmap = BitmapFactory.decodeFile(path, bmpOptions) // agora lê o bitmap com a escala definida
        when (view) {
            is ImageView -> (view as ImageView).setImageBitmap(bitmap)
            //else -> view.background = bitmap.toDrawable(view.resources)
            else -> view.background = BitmapDrawable(view.resources, bitmap)
        }
    }
}