package pt.pedrocorreia.sketches

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class DrawingArea @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), GestureDetector.OnGestureListener {

    companion object {
        private const val TAG = "DrawingArea"
    }

    val paint = Paint(Paint.DITHER_FLAG).also {
        it.color = Color.BLACK
        it.strokeWidth = 4.0f
        it.style = Paint.Style.FILL_AND_STROKE
    }

    var backColor : Int = Color.WHITE

    constructor(context: Context, color: Int) : this(context){
       this.backColor = color
        setBackgroundColor(backColor)
    }

    private val gestureDetector : GestureDetector by lazy {
        GestureDetector(context, this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(50f,50f,250f,250f, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(gestureDetector.onTouchEvent(event)){
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        Log.i(TAG, "onDown ")
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
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        Log.i(TAG, "onLongPress")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        Log.i(TAG, "onFling")
        return false
    }
}