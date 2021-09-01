package com.example.yangchaoming.bappdemo.b_app_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.b_app_demo.bean.LessonRingData
import kotlin.math.abs
import kotlin.math.min

class LessonRingView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,  style:Int = 0): View(context,attrs,style){
    private  val TAG = "LessonRingView"
    private var ringRadius :Int= 0
    private var innerRadius :Int = 0

    private var data:ArrayList<LessonRingData> = ArrayList()

    private var totalValue:Float = 0f

    private var rectF:RectF = RectF()

    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {

        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null)return
        if(data.isNullOrEmpty()){
            canvas.drawColor(ContextCompat.getColor(context,android.R.color.transparent))
            return
        }
        canvas.translate(ringRadius.toFloat(),ringRadius.toFloat())
        canvas.rotate(-90f)
        if(totalValue == 0f)return

        for (i in 0 until data.size){
            val lessonRingData = data[i]
            paint.color = Color.parseColor(lessonRingData.color)
            val radius = (abs((lessonRingData.value / totalValue)) * 360)
            Log.e(TAG, "onDraw: radius : $radius" )
            if(radius>0){
                canvas.drawArc(rectF,0f,radius+1,false,paint) //加1 是为了防止 圆环出现间隙
            }
            canvas.rotate(radius)
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
        val sizeH = MeasureSpec.getSize(heightMeasureSpec)
        val contentLength = min(sizeW,sizeH)
        setMeasuredDimension(contentLength,contentLength)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ringRadius = min(w,h)/2
        innerRadius = (ringRadius*0.60).toInt()
//        paint.strokeWidth = dp2px(ringRadius -innerRadius )
        paint.strokeWidth = ((ringRadius - innerRadius).toFloat())

        rectF.set((-ringRadius + paint.strokeWidth/2),
                (-ringRadius + paint.strokeWidth/2),
                ringRadius.toFloat() - paint.strokeWidth/2,
                ringRadius.toFloat() - paint.strokeWidth/2)
    }


    public fun setData(_data:ArrayList<LessonRingData>){
        data.clear()
        data.addAll(_data)
        totalValue = 0f
        for (i in 0 until data.size){
            totalValue += abs(data[i].value)
        }
        invalidate()
    }

    private fun dp2px( value: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value.toFloat(),resources.displayMetrics)
    }
}