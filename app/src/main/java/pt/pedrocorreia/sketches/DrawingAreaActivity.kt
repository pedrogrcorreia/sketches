package pt.pedrocorreia.sketches

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding


class DrawingAreaActivity : AppCompatActivity() {
    lateinit var binding: ActivityDrawingAreaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val r : Int = intent.getIntExtra("Red", 255)
        val g = intent.getIntExtra("Green", 255)
        val b = intent.getIntExtra("Blue", 255)
        val title = intent.getStringExtra("Title") ?: getString(R.string.str_no_name)

        binding.frLayout.setBackgroundColor(Color.rgb(r, g, b))
        supportActionBar?.title = "${getString(R.string.sketches)}: $title"
    }
}