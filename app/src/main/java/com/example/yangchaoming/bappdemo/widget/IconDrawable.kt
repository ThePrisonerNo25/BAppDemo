package com.example.yangchaoming.bappdemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.TypedValue

class IconDrawable(private val icon :Drawable,private val backGroundColor:Int,val context: Context):Drawable() {

    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var desiredIconWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80f,context.resources.displayMetrics).toInt()
    var desiredIconHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80f,context.resources.displayMetrics).toInt()
    init {
        paint.color = backGroundColor
    }

    override fun draw(canvas: Canvas) {
        val rect = bounds
        canvas.drawCircle(rect.centerX().toFloat(),rect.centerY().toFloat(),rect.centerX().toFloat(),paint)
        icon.setBounds(bounds.centerX() - (desiredIconWidth / 2), bounds.centerY() - (desiredIconHeight / 2), (bounds.centerX() - (desiredIconWidth / 2)) + desiredIconWidth, (bounds.centerY() - (desiredIconHeight / 2)) + desiredIconHeight);
        //draw the icon to the bounds
        icon.draw(canvas)

    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}