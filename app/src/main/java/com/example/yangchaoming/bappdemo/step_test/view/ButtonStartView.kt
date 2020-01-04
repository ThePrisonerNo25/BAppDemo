package com.example.yangchaoming.bappdemo.step_test.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.yangchaoming.bappdemo.R

class ButtonStartView  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr){
    private lateinit var ivStart:ImageView
    private lateinit var tvStart:TextView
    private lateinit var mPaint:Paint
    public var isRunning:Boolean=false
    public var isStart:Boolean=true
    init {
        LayoutInflater.from(context).inflate(R.layout.button_start,this,true)
        ivStart=findViewById(R.id.iv_start)
        tvStart=findViewById(R.id.tv_start)

        mPaint= Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color= Color.parseColor("#eab807")
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas==null)return
        super.onDraw(canvas)
        mPaint.color= Color.parseColor(if(isRunning) "#eab807" else "#2ac98e")
        canvas.drawCircle(canvas.width/2.0f,canvas.height/2.0f,canvas.width/2.0f,mPaint)
    }

    fun setRunState(running:Boolean){
        isStart=false
        if(isRunning!=running){
            tvStart.text=if(running) "暂停" else "继续"
//            ivStart.setBackgroundColor(Color.parseColor("#"))
            mStateChangeListener?.stateChanged(running)
            isRunning=running
            invalidate()
        }
    }

    fun setRestart(){
        isStart=true
        isRunning=false
        tvStart.text="开始"
        mStateChangeListener?.restart()
        invalidate()
    }


    interface StateChangeListener {
        fun stateChanged(isRunning:Boolean)
        fun restart()
    }

    public var mStateChangeListener:StateChangeListener? = null
}