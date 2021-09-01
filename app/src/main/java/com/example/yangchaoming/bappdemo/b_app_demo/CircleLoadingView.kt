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

class CircleLoadingView @JvmOverloads constructor(context: Context,attrs:AttributeSet? = null,defStyle:Int=0):View(context, attrs,defStyle){
    private val circleBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//圆环背景
    private val circleProgressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//圆环进度
    private val progressTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//进度文本 ----99%
    private val progressTipsPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)//进度提示语 -------下载中。。

    var strokeWidth = CommonUtil.dp2px(1f,context)
    var viewWidth = 0
    var viewHeight = 0

    private var progress = 0

    private var defaultTextSize = 60f
    private var defaultTipsSize = 30f
    companion object{
       val MAX_STRING = "100%"
    }

    init {
        circleBackgroundPaint.color = ContextCompat.getColor(context, R.color.color_txt_main3)
        circleBackgroundPaint.strokeCap = Paint.Cap.ROUND
        circleBackgroundPaint.strokeWidth = strokeWidth.toFloat()

        circleProgressPaint.color = ContextCompat.getColor(context, R.color.color_main)
        circleProgressPaint.strokeCap = Paint.Cap.ROUND
        circleProgressPaint.strokeWidth = strokeWidth.toFloat()

        progressTextPaint.color = ContextCompat.getColor(context, R.color.color_txt_main1)
        //30sp
        progressTextPaint.textSize = (defaultTextSize * resources.displayMetrics.scaledDensity)

        progressTipsPaint.color = ContextCompat.getColor(context, R.color.color_txt_main2)
        progressTipsPaint.textSize = (defaultTipsSize * resources.displayMetrics.scaledDensity)
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

        //计算TextSize
        defaultTextSize = setItemTextSize(3,progressTextPaint,defaultTextSize)
        defaultTipsSize = setItemTextSize(10,progressTipsPaint,defaultTipsSize)


    }

    /**
     * 递归设置最适合的textsize
     * @param ratio
     * @param paint
     * @param textSize
     * @return
     */
    private fun setItemTextSize(ratio:Int,paint:Paint,textSize:Float):Float{
        paint.textSize = (textSize * resources.displayMetrics.scaledDensity)
        val fontMetrics = paint.fontMetrics
        val text_height = fontMetrics.descent - fontMetrics.ascent //获取文字高度
        Log.e("setItemTextSize", "setItemTextSize: $text_height   ${viewWidth/ratio}");
        var textSize_ = textSize

        if(text_height<viewWidth/ratio){
            return textSize_
        }else{
            textSize_--
            setItemTextSize(ratio,paint,textSize_)
        }

        return textSize_
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)return
        canvas.translate(viewWidth/2.0f,viewHeight/2.0f)


        //draw Text-----100%
        val text = "${progress}%"
        val text_witdh = progressTextPaint.measureText(text) //获取文字宽
        val t_x = -text_witdh/2.0f
        val fontMetrics = progressTextPaint.fontMetrics
        val text_height = fontMetrics.descent - fontMetrics.ascent //获取文字高度
        val t_y = text_height *0.2f
        canvas.drawText(text,t_x,t_y,progressTextPaint)

        //画tips
        val text_tips = if(progress<100) "下载中..." else "下载完成"
        val tips_witdh = progressTipsPaint.measureText(text_tips) //获取文字宽
        val tips_font_metrics = progressTipsPaint.fontMetrics
        val tips_height = tips_font_metrics.descent - tips_font_metrics.ascent //获取文字高度
        val tips_x = -tips_witdh/2.0f
        val tips_y = viewHeight/4.0f
        canvas.drawText(text_tips,tips_x,tips_y,progressTipsPaint)

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