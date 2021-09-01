package com.example.yangchaoming.bappdemo.b_app_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R

class Battery @JvmOverloads constructor(context: Context,attrs:AttributeSet? = null,defStyle :Int = 0): View(context,attrs,defStyle) {
    var batteryNum : Float //电量百分比
    var viewWidth = 0 //控件宽
    var viewHeight = 0 //控件高

    var batteryBackgroundRect = RectF()
    var batteryHeadRect = RectF()
    var batteryRect = RectF()

    var backgroundPaint : Paint
    var batteryHeadPaint : Paint
    var batteryPaint : Paint

    val fillet = 4f
    val padding = 3f
    val strokeWidth = 3f

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.BatteryView)
        batteryNum = ta.getFraction(R.styleable.BatteryView_powerValue, 1, 1, 100f)
        ta.recycle()

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = ContextCompat.getColor(context,R.color.colorBlack)
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.strokeWidth = strokeWidth


        batteryHeadPaint  = Paint(Paint.ANTI_ALIAS_FLAG)
        batteryHeadPaint.color = ContextCompat.getColor(context,R.color.colorBlack)

        batteryPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        batteryPaint.color = ContextCompat.getColor(context,R.color.color_main)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null || viewWidth <= 0 || viewHeight <= 0)return
        //画电池电量框
        batteryBackgroundRect.set(strokeWidth,strokeWidth,viewWidth*0.95f,viewHeight*0.95f)
        canvas.drawRoundRect(batteryBackgroundRect,fillet,fillet,backgroundPaint)
        //画电池头
        batteryHeadRect.set(batteryBackgroundRect.right,
                viewHeight/2.0f-viewHeight*0.2f,
                batteryBackgroundRect.right+viewWidth*0.05f,
                viewHeight/2.0f+viewHeight*0.2f)
        canvas.drawRoundRect(batteryHeadRect,fillet,fillet,batteryHeadPaint)

        //画电池电量
        var radio = 1.0f
        when {
            batteryNum>1 -> radio = 1.0f
            batteryNum<0 -> radio = 0f
            else -> radio = batteryNum
        }

        batteryRect.set(strokeWidth+padding,
                strokeWidth+padding,
                viewWidth*0.95f*radio - padding,
                viewHeight*0.95f - padding)
        canvas.drawRect(batteryRect,batteryPaint)
    }

    fun setBatteryFraction(batteryNum:Float){
        this.batteryNum = batteryNum
        invalidate()
    }
}