package com.example.yangchaoming.bappdemo.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R

class RotateAddIcon@JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyle:Int=0): ConstraintLayout(context,attrs,defStyle){
    lateinit var addBackground : ImageView
    lateinit var addFront : ImageView

    companion object{
        const val LIGHT = 0
        const val DARK = 1
    }
    var rotateStyle = LIGHT
    init {
        LayoutInflater.from(context)
                .inflate(R.layout.rotate_add_icon, this,true)
        initializeView()
    }

    private fun initializeView(){
        addBackground = findViewById(R.id.iv_background)
        addFront = findViewById(R.id.iv_front)

        val rotation = ObjectAnimator.ofFloat(addBackground, "rotation", 0f, 360f);
        rotation.duration = 2500
        rotation.interpolator = LinearInterpolator()
        rotation.repeatCount = -1
        rotation.start()
    }

    public fun setStyle(style:Int){
        rotateStyle = style
        if(style == LIGHT){
            addBackground.drawable.setTint(ContextCompat.getColor(context,R.color.white))
            addFront.drawable.setTint(ContextCompat.getColor(context,R.color.white))
        }else{
            addBackground.drawable.setTint(ContextCompat.getColor(context,R.color.colorBlack1))
            addFront.drawable.setTint(ContextCompat.getColor(context,R.color.colorBlack1))
        }

    }
}