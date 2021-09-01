package com.example.yangchaoming.bappdemo.b_app_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.utils.CommonUtil
import kotlin.math.roundToInt

class CircleProgresView @JvmOverloads constructor(context: Context, attrs:AttributeSet? = null, defStyle:Int=0):View(context, attrs,defStyle){
    private val circleBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//圆环背景
    private val circleProgressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//圆环进度

    var strokeWidth = CommonUtil.dp2px(1f,context)
    var viewWidth = 0
    var viewHeight = 0

    private var progress = 0


    init {
        circleBackgroundPaint.color = ContextCompat.getColor(context, R.color.color_txt_main3)
        circleBackgroundPaint.strokeCap = Paint.Cap.ROUND
        circleBackgroundPaint.strokeWidth = strokeWidth.toFloat()

        circleProgressPaint.color = ContextCompat.getColor(context, R.color.color_main)
        circleProgressPaint.strokeCap = Paint.Cap.ROUND
        circleProgressPaint.strokeWidth = strokeWidth.toFloat()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        if(viewWidth < viewHeight){
            viewHeight =  viewWidth
        } else{
            viewWidth = viewHeight
        }

        //计算strokeWidth
        strokeWidth = viewWidth/64
        circleBackgroundPaint.strokeWidth = strokeWidth.toFloat()
        circleProgressPaint.strokeWidth = strokeWidth.toFloat()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)return
        canvas.translate(viewWidth/2.0f,viewHeight/2.0f)



        //画圆环
        val ring_item_w = viewWidth/20.0f
        val ring_start_x =  viewWidth/2 - ring_item_w
        val ring_start_y = 0f
        val ring_stop_x = (viewWidth/2 - strokeWidth).toFloat()
        val ring_stop_y = 0f

        val j = (progress/100.00f*60).toInt()
        for (i in 0 until 60){
            canvas.drawLine(ring_start_x,ring_start_y,ring_stop_x,ring_stop_y,circleBackgroundPaint)
            if(i<=j){
                canvas.drawLine(ring_start_x,ring_start_y,ring_stop_x,ring_stop_y,circleProgressPaint)
            }
            canvas.rotate(360/60f)
        }

    }

    fun setProgressValue(progress:Int){
        this.progress = progress
        if(progress <= 0)this.progress = 0
        if(progress >= 100)this.progress = progress
        invalidate()
    }
}