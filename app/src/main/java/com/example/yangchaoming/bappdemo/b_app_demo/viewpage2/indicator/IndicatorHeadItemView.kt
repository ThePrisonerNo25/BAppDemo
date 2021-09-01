package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2.indicator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.yangchaoming.bappdemo.R
import kotlin.math.max

class IndicatorHeadItemView:
    ConstraintLayout{
    var TAG="IndicatorHeadItemView"
    lateinit var iv:ImageView
    lateinit var tv:TextView
    var imageViewH:Int = context.resources.getDimensionPixelOffset(R.dimen.pageMargin)*2

    constructor(context: Context): super(context)
    constructor(context: Context, attrSet: AttributeSet): super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int): super(context, attrSet, defStyleAttr)

    init {
//        orientation = LinearLayout.HORIZONTAL
//        LayoutInflater.from(context).inflate(R.layout.item_indicator_head, this, true)
//        iv = findViewById(R.id.iv_head)
//        tv = findViewById(R.id.tv_head)
//        setBackgroundResource(R.color.red)

        val view = LayoutInflater.from(context).inflate(R.layout.item_indicator_head1, this, false)
        val set = ConstraintSet()
        addView(view)
        set.match(view, this)


//        LayoutInflater.from(context).inflate(R.layout.item_indicator_head1, this, true)
//        iv = findViewById(R.id.iv_head)
//        tv = findViewById(R.id.tv_head)

//        val set = ConstraintSet()
//        set.clone(this)
//        this.setConstraintSet(set)
//        set.match(this, this)
        setBackgroundResource(R.color.red)
    }

    fun ConstraintSet.match(view: View, parentView: View) {
        this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
        this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
        this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
        this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
    }

//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        Log.e("onLayout", "onLayout: 222222222----$top  -----$left----- $bottom")
//
//        for (i in 0 until childCount){
//            val childAt = getChildAt(i)
//            childAt.layoutParams
//        }
//
//        super.onLayout(changed, left, top, right, bottom)
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val size = MeasureSpec.getSize(heightMeasureSpec)
//        Log.e(TAG, "onMeasure:22222  height=  $size" )
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }

//    fun setImage(image:String?){
//
//    }
//
//    fun setName(name:String?){
//        tv.setText(name)
//    }

}