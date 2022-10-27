package pt.pedrocorreia.sketches

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding


class DrawingAreaActivity : AppCompatActivity() {
    companion object {
        const val TITLE_KEY = "title"
        const val COLOR_KEY = "color"

        fun getIntent(
            context: Context,
            title: String,
            background_color: Int
        ) : Intent {
            val intent = Intent(context, DrawingAreaActivity::class.java)
            intent.putExtra(TITLE_KEY, title)
            intent.putExtra(COLOR_KEY, background_color)
            return intent
        }
    }

    lateinit var drawingArea : DrawingArea

    lateinit var binding: ActivityDrawingAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
//        val r : Int = intent.getIntExtra("Red", 255)
//        val g = intent.getIntExtra("Green", 255)
//        val b = intent.getIntExtra("Blue", 255)
        val color = intent.getIntExtra(COLOR_KEY, Color.WHITE)
        val title = intent.getStringExtra(TITLE_KEY) ?: getString(R.string.str_no_name)

//        binding.frLayout.setBackgroundColor(Color.rgb(r, g, b))
//        binding.frLayout.setBackgroundColor(color)
        supportActionBar?.title = "${getString(R.string.sketches)}: $title"

        drawingArea = DrawingArea(this, color)
        binding.frLayout.addView(drawingArea)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawing_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.mnSave -> {}
            item.groupId == R.id.grpColors -> {
                item.isChecked = true
                drawingArea.lineColor = when(item.itemId) {
                    R.id.mnWhite -> Color.WHITE
                    R.id.mnBlue -> Color.BLUE
                    R.id.mnYellow -> Color.YELLOW
                    R.id.mnBlack -> Color.BLACK
                    else -> Color.BLACK
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}