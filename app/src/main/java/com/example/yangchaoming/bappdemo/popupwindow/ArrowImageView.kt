package com.example.yangchaoming.bappdemo.popupwindow

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R

class ArrowImageView @JvmOverloads constructor( context: Context, attrs: AttributeSet?=null, defStyleAttr:Int = 0): AppCompatImageView(context,attrs,defStyleAttr){
    val arrowDrawble = ArrowDrawble(ContextCompat.getColor(context, R.color.colorPrimary), Gravity.TOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        arrowDrawble.setBounds(0,0,w,h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas == null)return
        arrowDrawble.draw(canvas)
    }
}