package com.example.yangchaoming.bappdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

class MyView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?=null,style:Int=0):View(context,attributeSet,style){

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var _width = 0
    private var _height =0

    private var textPath = Path()
    private var path = Path()

    private var textString = "代金"
    private var textRect = Rect()
    init {
        paint.color = ContextCompat.getColor(context, R.color.red)

        textPaint.textSize =sp2px(12f)
        textPaint.getTextBounds(textString,0,textString.length,textRect)
        textPaint.color = ContextCompat.getColor(context, R.color.white)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _width = w
        _height = h

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)return



        path.addArc(0f,0f,_width*2/5f,_width*2/5f,-180f,90f)
        path.lineTo(_width.toFloat(),0f)
        path.lineTo(0f,_height.toFloat())
        path.lineTo(0f,_width/5f)
        canvas.drawPath(path,paint)

        textPath.moveTo(0f,_height.toFloat())
        textPath.lineTo(_width.toFloat(),0f)
        val sqrt = sqrt(_width.toDouble().pow(2) + _height.toDouble().pow(2))
        val offsetStart = (sqrt-textRect.right)/2f
        canvas.drawTextOnPath(textString,textPath, offsetStart.toFloat(),-10f,textPaint)
    }


    fun dp2px(value:Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
    }

    fun sp2px(value:Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)
    }
}