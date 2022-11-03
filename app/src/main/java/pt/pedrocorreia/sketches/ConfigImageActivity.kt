package pt.pedrocorreia.sketches

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import pt.pedrocorreia.sketches.databinding.ActivityConfigImageBinding

class ConfigImageActivity : AppCompatActivity() {

    companion object {
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

    private fun chooseImage(){
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
        } else{
            binding.frPreview.background = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_report_image, null)
        }
    }


}