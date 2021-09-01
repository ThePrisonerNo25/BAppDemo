package com.example.yangchaoming.bappdemo.b_app_demo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.example.yangchaoming.bappdemo.R

class WeightHistoryView @JvmOverloads constructor(context: Context,attr:AttributeSet,defaultStyle:Int=0) :FrameLayout(context,attr,defaultStyle) {
    var progress_bar:WeightProgressBar? =null
    var tv_pre_weight:TextView? =null
    var tv_post_weight:TextView? =null
    init {
        val view =  LayoutInflater.from(context).inflate(R.layout.weight_history,this,false)
        progress_bar = view.findViewById(R.id.progress_bar)
        tv_pre_weight = view.findViewById(R.id.tv_pre_weight)
        tv_post_weight = view.findViewById(R.id.tv_post_weight)
        addView(view)
    }


    fun setProgressAndValue(_progressValue: Float, txt: String?){
        progress_bar?.setProgressAndValue(_progressValue,txt)
    }
}