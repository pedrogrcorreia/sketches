package pt.pedrocorreia.sketches

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import pt.pedrocorreia.sketches.databinding.ActivityDrawingAreaBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DrawingAreaActivity : AppCompatActivity() {
    companion object {
        const val TITLE_KEY = "title"
        const val COLOR_KEY = "color"
        const val IMAGE_KEY = "imageFile"

        fun getIntent(
            context: Context,
            title: String,
            background_color: Int,
        ) : Intent {
            val intent = Intent(context, DrawingAreaActivity::class.java)
            intent.putExtra(TITLE_KEY, title)
            intent.putExtra(COLOR_KEY, background_color)
            return intent
        }

        fun getIntent(
            context: Context,
            title: String,
            imagePath: String,
        ) : Intent {
            val intent = Intent(context, DrawingAreaActivity::class.java)
            intent.putExtra(TITLE_KEY, title)
            intent.putExtra(IMAGE_KEY, imagePath)
            return intent
        }
    }

    lateinit var drawingArea : DrawingArea

    lateinit var binding: ActivityDrawingAreaBinding

    private var title : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawingAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val color = intent.getIntExtra(COLOR_KEY, Color.WHITE)
        title = intent.getStringExtra(TITLE_KEY) ?: getString(R.string.str_no_name)
        val imagePath = intent.getStringExtra(IMAGE_KEY)
        supportActionBar?.title = "${getString(R.string.sketches)}: $title"

        drawingArea = if(imagePath == null) {
            DrawingArea(this, color)
        } else{
            DrawingArea(this, imagePath)
        }
        binding.frLayout.addView(drawingArea)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawing_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.mnSave -> {
                val bitmap : Bitmap = drawingArea.drawToBitmap()
                val savePath = saveSketchLocation(this, title!!)
                saveSketch(savePath, bitmap)
            }
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