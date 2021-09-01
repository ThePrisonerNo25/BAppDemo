package com.example.yangchaoming.bappdemo.behavior_demo.demo2.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.example.yangchaoming.bappdemo.R

class BehaviorBottom @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){
    companion object{
        private const val TAG = "BehaviorBottom"
    }
    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val clHead = parent.findViewById<View>(R.id.cl_head)
        val nest_middle = parent.findViewById<View>(R.id.nest_middle)
        val top = clHead.measuredHeight+nest_middle.measuredHeight
        val bottom =parent.measuredHeight +  clHead.measuredHeight+nest_middle.measuredHeight
        child.layout(0,top , child.measuredWidth, bottom)
        return true
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val findViewById = parent.findViewById<View>(R.id.nest_middle)
        return dependency == findViewById
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

//        child.translationY = -dependency.top.toFloat()
        child.translationY = dependency.translationY
        return true
    }

//    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
////        Log.e("onStartNestedScroll", "onStartNestedScroll: ");
//        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
//    }
//
//    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
//        Log.e(TAG, "onNestedPreScroll: $dy --- consumed = ${consumed[1]}" )
////        consumed[1] = dy
//    }
//
//    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
//        Log.e(TAG, "onNestedScroll:consumed[1] =  ${consumed[1]} ------dyConsumed = $dyConsumed ----- dyUnconsumed = $dyUnconsumed ")
//    }
}