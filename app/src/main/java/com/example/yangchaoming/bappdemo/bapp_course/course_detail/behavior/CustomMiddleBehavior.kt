package com.example.yangchaoming.bappdemo.bapp_course.course_detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.example.yangchaoming.bappdemo.R
import com.google.android.material.tabs.TabLayout


class CustomMiddleBehavior  @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){
    private var tabLayoutHeight = 0
    private var headViewHeight = 0

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.video_view
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        return super.onDependentViewChanged(parent, child, dependency)
        val bottom = dependency.bottom
        child.y = bottom.toFloat()
//        Log.e("onDependentViewChanged", "onDependentViewChanged: ${child.translationY}");
        return true
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        if(tabLayoutHeight == 0){
            val tabLayout = coordinatorLayout.findViewById<TabLayout>(R.id.tab_layout)
            tabLayoutHeight =tabLayout?.height ?: 0
        }

        if(headViewHeight == 0){
            val headView = coordinatorLayout.findViewById<View>(R.id.video_view)
            headViewHeight =headView?.height ?: 0
        }

        return axes and ViewCompat.SCROLL_AXIS_VERTICAL !== 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

        val limitHeight = child.height-tabLayoutHeight - headViewHeight
        var finalY =  child.translationY - dy

        if(dy > 0){//页面向上滚动
            if(-finalY>limitHeight){
                finalY = -limitHeight.toFloat()
            }else{
                consumed[1] = dy
            }
            child.translationY = finalY
        }
//        if(-finalY> limitHeight){
//            finalY = -limitHeight.toFloat()
//        }else if(-finalY<0){
//            finalY = 0f
//        }else{
//            consumed[1] = dy
//        }
//        child.translationY = finalY
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
//        Log.e("onNestedScroll", "onNestedScroll: $dyUnconsumed");
        if(dyUnconsumed<0){//页面向下滚动
            var finalY =  child.translationY - dyUnconsumed
            if(finalY>headViewHeight){
                finalY = headViewHeight.toFloat()
            }
            consumed[1] = dyUnconsumed
            child.translationY = finalY
        }
    }

//    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
//        super.onStopNestedScroll(coordinatorLayout, child, target, type)
//        Log.e("onStopNestedScroll", "onStopNestedScroll: ");
//    }
}