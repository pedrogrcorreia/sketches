package pt.pedrocorreia.sketches

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import pt.pedrocorreia.sketches.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSolidColor : Button = findViewById(R.id.solidColor)
        val btnGallery : Button = findViewById(R.id.gallery)
        val btnPhoto : Button = findViewById(R.id.photo)
        val btnHistory : Button = findViewById(R.id.history)
        btnSolidColor.setOnClickListener {
            intent = Intent(this, ConfigColorActivity::class.java)
            startActivity(intent)
        }
        btnGallery.setOnClickListener{
            startActivity(ConfigImageActivity.getGalleryIntent(this))
        }
        btnPhoto.setOnClickListener{
            startActivity(ConfigImageActivity.getCameraIntent(this))
        }
        btnHistory.setOnClickListener(makeSnackbar)
    }

    // Function to retrieve a toast on a button click
    override fun onClick(p0: View?) {
        val btn = p0 as Button
        Toast.makeText(this,"${btn.text}: ${getString(R.string.todo)}", Toast.LENGTH_LONG).show()
    }

    private val makeSnackbar = View.OnClickListener {
        Snackbar.make(it, "${(it as Button).text}: ${getString(R.string.todo)}", Snackbar.LENGTH_LONG).show()
    }
}