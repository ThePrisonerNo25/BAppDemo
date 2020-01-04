package com.example.yangchaoming.bappdemo.step_test.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.math.BigDecimal
import java.math.RoundingMode


class TimeProgressView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? =null,defStyleAttr: Int =0 )
    : View(context,attributeSet,defStyleAttr){
    val tag = "TimeProgressView";
    private var mRingPaint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTextPaint:Paint =Paint(Paint.ANTI_ALIAS_FLAG)

    var maxValue:Float=100*1000f

    var currentValue:Float = 0f
        set(value) = run{
        field = if(value<0) 0f else value
        invalidate()
    }

    init {
        mRingPaint.style=Paint.Style.STROKE
        mRingPaint.strokeCap=Paint.Cap.ROUND
        mRingPaint.strokeWidth=dp2px(8f)
        mRingPaint.color=Color.parseColor("#ffffff")

        mTextPaint.textSize=sp2px(80f)
        mTextPaint.color=Color.parseColor("#ffffff")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(if (width > height) height else width, if (width > height) height else width)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas==null)return
        val width = canvas.width
        val height=canvas.height
        canvas.translate(width/2.0f,height/2.0f)

        //画数字
        var text = (currentValue/1000).toInt().toString()
        var rect= Rect()
        mTextPaint.getTextBounds(text,0,text.length,rect)
        val visualDiff=dp2px(4f)//手动 调节人的视觉产生的 误差
        var x =-rect.width()/2.0f-visualDiff
        var y=rect.height()/2.0f
        canvas.drawText(text,x,y,mTextPaint)

        //画圆环
        var percent= currentValueToPercent(currentValue,maxValue)
        var paintStrokeWidth= mRingPaint.strokeWidth
        var circleBound= RectF(-width/2.0f + paintStrokeWidth,-height/2.0f + paintStrokeWidth,width/2.0f - paintStrokeWidth,height/2.0f - paintStrokeWidth)
        canvas.drawArc(circleBound!!,-90f,360*percent,false,mRingPaint)
    }

    private fun currentValueToPercent(currentValue:Float,maxValue:Float) : Float{
        if(maxValue==0f||currentValue==0f)return 0f
        if(currentValue>=maxValue)return 1f
        val percent = currentValue.toDouble() / maxValue.toDouble()
        val bigDecimal = BigDecimal.valueOf(percent)
        return  bigDecimal.setScale(5, RoundingMode.HALF_UP).toFloat()
    }


    private fun dp2px(value:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
    }

    private fun sp2px(value:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)
    }




}