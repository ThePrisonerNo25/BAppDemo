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

class WeightProgressBar @JvmOverloads constructor(context: Context, attr: AttributeSet, defaultStyle: Int = 0) : View(context, attr, defaultStyle) {
    var viewWidth: Int = 0
    var viewHeight: Int = 0

    var progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var outRingPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var innerRingPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var progressValue: Float = 0f
    var text: String? = ""

    var maxTextWidth: Float = 0f

    companion object {
        const val DEFAULT_WEIGHT = "1000.0kg"
    }

    var textWidth: Float = 0f

    init {
        progressPaint.color = ContextCompat.getColor(context, R.color.color_main)
//        progressPaint.style = Paint.Style.FILL

        textPaint.color = ContextCompat.getColor(context, R.color.text_color_3)

        //设置字体大小（12sp）
        textPaint.textSize = ((12f * resources.displayMetrics.scaledDensity).roundToInt()).toFloat()
//        textPaint.textSize = 12f

        outRingPaint.color = ContextCompat.getColor(context, R.color.text_color_2)
        innerRingPaint.color = ContextCompat.getColor(context, R.color.white)

        maxTextWidth = textPaint.measureText(DEFAULT_WEIGHT)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        //计算控件的高度
        val fontMetrics = textPaint.fontMetrics
        val maxTextHeight = -fontMetrics.top + fontMetrics.bottom
        val viewHeight = maxTextHeight * (30.0f / 15)
//        Log.e("TAG", "onMeasure:viewHeight= $viewHeight" )
        val realHeight = reconcileHeightSize(viewHeight.toInt(), heightMeasureSpec)
//        Log.e("TAG", "onMeasure:realHeight= $realHeight" )

        //计算控件的宽度
        val realWidth = reconcileWidthSize(maxTextWidth.toInt(), widthMeasureSpec)
//        val realWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec)
//        Log.e("TAG", "onMeasure:realWidth= $realWidth" )
        setMeasuredDimension(realWidth, realHeight)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //画进度条
//        val progressHeight = viewWidth * (4.00f / 220)
        val progressHeight = CommonUtil.dp2px(4f,context)
        val progressTop = viewHeight * (2.00f / 3)

        val outRingRadio = progressHeight * (5.0f / 4)
        val innerRingRadio = progressHeight * (3.5f / 4)

        canvas?.drawRect(outRingRadio, progressTop, viewWidth.toFloat()-outRingRadio, progressTop + progressHeight, progressPaint)

        //画圆环

        val cx = (viewWidth-2*outRingRadio) * progressValue + outRingRadio
        val cy = progressTop + progressHeight / 2
        canvas?.drawCircle(cx, cy, outRingRadio, outRingPaint)
        canvas?.drawCircle(cx, cy, innerRingRadio, innerRingPaint)

        //画文字
        var text_x = cx - textWidth / 2
        val text_y = cy - outRingRadio - CommonUtil.dp2px(2f,context)
        if(text_x<0) text_x =0f
        if(text_x+textWidth>viewWidth) text_x = viewWidth - textWidth
        if (!text.isNullOrEmpty()) canvas?.drawText(text!!, text_x, text_y, textPaint)
    }

    fun setProgressAndValue(_progressValue: Float, txt: String?) {
        if (!txt.isNullOrEmpty()){
            text = txt
            textWidth = textPaint.measureText(txt)
        }
        if(_progressValue>1){
            progressValue = 1f
        }else if(_progressValue<0){
            progressValue = 0f
        }else{
            progressValue = _progressValue
        }

        invalidate()
    }

    fun reconcileHeightSize(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> {
                return specSize
            }
            MeasureSpec.AT_MOST -> {
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            }
            MeasureSpec.UNSPECIFIED -> {
                return contentSize
            }
            else -> {
                return contentSize
            }
        }
    }

    fun reconcileWidthSize(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
//        Log.e("reconcileWidthSize", "reconcileWidthSize: $specSize");
        when (mode) {
            MeasureSpec.EXACTLY -> {
                return specSize
            }
            MeasureSpec.AT_MOST -> {
                if (contentSize < specSize) {
                    return specSize;
                } else {
                    return contentSize;
                }
            }
            MeasureSpec.UNSPECIFIED -> {
                return contentSize
            }
            else -> {
                return contentSize
            }
        }
    }

}