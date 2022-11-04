package pt.pedrocorreia.sketches

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

data class Line(val path: Path, val color: Int)

class DrawingArea @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), GestureDetector.OnGestureListener {

    companion object {
        private const val TAG = "DrawingArea"
    }

    private val paint = Paint(Paint.DITHER_FLAG).also {
        it.color = Color.BLACK
        it.strokeWidth = 4.0f
        it.style = Paint.Style.FILL_AND_STROKE
    }

    var lineColor = Color.BLACK
        set(value){
            field = value
            drawing.add(Line(Path(), value))
        }
//    val path = Path()
    val drawing : ArrayList<Line> = arrayListOf(
        Line(Path(), lineColor)
    )

    val path : Path
        get() = drawing.last().path

    var backColor : Int = Color.WHITE

    var imageFile : String? = null

    constructor(context: Context, color: Int) : this(context){
       this.backColor = color
        setBackgroundColor(backColor)
    }

    constructor(context: Context, imageFile: String?) : this(context){
        this.imageFile = imageFile
        setPic(this, imageFile!!)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageFile?.let {setPic(this, it)}
    }

    private val gestureDetector : GestureDetector by lazy {
        GestureDetector(context, this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawLine(50f,50f,250f,250f, paint)
        for(line in drawing) {
            canvas?.drawPath(line.path, paint.apply {
                color = line.color
            })
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(gestureDetector.onTouchEvent(event)){
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        Log.i(TAG, "onDown ")
        path.moveTo(p0!!.x, p0.y)
        invalidate()
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        Log.i(TAG, "onShowPress ")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        Log.i(TAG, "onSingleTapUp")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.i(TAG, "onScroll ${p0?.x} ${p1?.x}")
        path.lineTo(p1!!.x, p1.y)
        path.moveTo(p1.x, p1.y)
        invalidate()
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.i(TAG, "onLongPress")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.i(TAG, "onFling")
        return false
    }
}