package com.example.yangchaoming.bappdemo.behavior_demo.demo2.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.example.yangchaoming.bappdemo.R

class BehaviorMiddle @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs){

    companion object{
        private const val TAG = "BehaviorMiddle"
    }
    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val clHead = parent.findViewById<View>(R.id.cl_head)
        child.layout(0, clHead.measuredHeight, child.measuredWidth, child.measuredHeight + clHead.measuredHeight)
        return true
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
//        Log.e("onStartNestedScroll", "onStartNestedScroll: ");
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        Log.e(TAG, "onNestedPreScroll: $dy ---consumed = ${consumed[1]}");
        Log.e(TAG, "onNestedPreScroll: $dy ---consumed = ${consumed[1]}");
        val clHead = coordinatorLayout.findViewById<View>(R.id.cl_head)
        val iv_3 = coordinatorLayout.findViewById<View>(R.id.iv_3)
        val max_y = clHead.measuredHeight + child.measuredHeight -iv_3.measuredHeight

        val transitionY = child.translationY
        if(dy>0){//向上滑
            if(-transitionY>=max_y){//达到最大偏移量
                child.translationY = (-max_y).toFloat()
            }else{
                child.translationY = child.translationY-dy
                consumed[1] = dy
            }
        }
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type)
//        Log.e(TAG, "onNestedScrollAccepted: ")
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedScroll:consumed[1] =  ${consumed[1]} ------dyConsumed = $dyConsumed ----- dyUnconsumed = $dyUnconsumed ")
        val head = coordinatorLayout.findViewById<View>(R.id.cl_head)
        val max_y = head.measuredHeight + child.measuredHeight
        val transitionY = child.translationY
        if(dyUnconsumed<0){//向下拉到顶
            if(transitionY<0){
                child.translationY = child.translationY-dyUnconsumed
                consumed[1] = dyUnconsumed
            }else{
                child.translationY = 0f
            }

        }
    }
}