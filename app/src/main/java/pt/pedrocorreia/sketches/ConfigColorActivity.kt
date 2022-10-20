package pt.pedrocorreia.sketches

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import pt.pedrocorreia.sketches.databinding.ActivityConfigColorBinding
import pt.pedrocorreia.sketches.databinding.ActivityMainBinding

class ConfigColorActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigColorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigColorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Red bar
        binding.seekRed.max = 255
        binding.seekRed.progress = 255
        binding.seekRed.setOnSeekBarChangeListener(procSeekBarChange)

        // Green bar
        binding.seekGreen.max = 255
        binding.seekGreen.progress = 255
        binding.seekGreen.setOnSeekBarChangeListener(procSeekBarChange)

        // Blue bar made with scope function to demo
        binding.seekBlue.run {
            max = 255
            progress = 255
            setOnSeekBarChangeListener(procSeekBarChange)
        }

        updateView()
    }

    private val procSeekBarChange = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            updateView()
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }

    fun updateView(){
        var color = Color.rgb(binding.seekRed.progress, binding.seekGreen.progress, binding.seekBlue.progress)
        binding.frPreview.setBackgroundColor(color)
    }
}