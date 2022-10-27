package pt.pedrocorreia.sketches

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import pt.pedrocorreia.sketches.databinding.ActivityConfigImageBinding
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding

class ConfigImageActivity : AppCompatActivity() {

    companion object {
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
    }

    private fun chooseImage(){
        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
    }

    private fun takePhoto(){
        Toast.makeText(this, R.string.todo, Toast.LENGTH_LONG).show()
    }
}