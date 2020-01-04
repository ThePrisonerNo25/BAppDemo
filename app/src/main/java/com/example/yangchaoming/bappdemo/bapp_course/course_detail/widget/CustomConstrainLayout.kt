package com.example.yangchaoming.bappdemo.bapp_course.course_detail.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild2
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import android.view.MotionEvent



class CustomConstrainLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context,attrs,defStyleAttr),NestedScrollingChild2{
    private lateinit var childHelper: NestedScrollingChildHelper

    init {
        init()
    }

    private val offset = IntArray(2) //偏移量
    private val consumed = IntArray(2) //消费
    private var lastY: Int = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                //记录触摸时的Y轴方向
                lastY = event.rawY.toInt()
            MotionEvent.ACTION_MOVE -> {
                val y = event.rawY.toInt()
                val dy = y - lastY//dy为屏幕上滑动的偏移量
                lastY = y
                dispatchNestedPreScroll(0, dy, consumed, offset)
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
        }

        return true
    }

    private fun init() {
        childHelper = NestedScrollingChildHelper(this)
        childHelper.isNestedScrollingEnabled = true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
//        Log.e("setNestedScrollin", "setNestedScrollingEnabled:$enabled ");
        childHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
//        Log.e("isNestedScrollin", "isNestedScrollingEnabled: ");
        return childHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
//        Log.e("startNestedScroll", "startNestedScroll: ");
        return childHelper.startNestedScroll(axes,type)
    }

    override fun stopNestedScroll(type: Int) {
//        Log.e("stopNestedScroll", "stopNestedScroll: ");
        return childHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
//        Log.e("hasNestedScrolling", "hasNestedScrollingParent: ");
        return childHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
//        Log.e("dispatchNestedScroll", "dispatchNestedScroll: ");
       return childHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow,type)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?, type: Int): Boolean {
//        Log.e("dispatchNestedPreScroll", "dispatchNestedPreScroll: ");
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY)
    }


}