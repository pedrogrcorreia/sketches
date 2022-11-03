package pt.pedrocorreia.sketches

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import pt.pedrocorreia.sketches.databinding.ActivityConfigImageBinding
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding

class ConfigImageActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val MODE_KEY = "mode"

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
    }

    private fun verifyPermissions(){
        if ( ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        )  {
            permissionsGranted = false
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            permissionsGranted = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED}
            return
        }
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults)
    }

    private fun chooseImage(){
        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
    }

    private fun takePhoto(){
        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
    }
}