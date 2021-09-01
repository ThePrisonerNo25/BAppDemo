package com.example.yangchaoming.bappdemo.behavior_demo.demo1.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.example.yangchaoming.bappdemo.R

class BehaviorContent @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){
    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val head = parent.findViewById<ConstraintLayout>(R.id.cl_head)
        val head_img = parent.findViewById<ConstraintLayout>(R.id.head_img)
        Log.e("onLayoutChild", "onLayoutChild: height:${head.measuredHeight}  -------width: ${child.measuredHeight}");

        child.layout(0,head_img.measuredHeight,child.measuredWidth,child.measuredHeight+head_img.measuredHeight)

        return true
    }

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        val childLpHeight = child.layoutParams.height
        if(childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT || childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT){
            val head = parent.findViewById<ConstraintLayout>(R.id.cl_head)
            getChildSize(head, parentWidthMeasureSpec, parentHeightMeasureSpec, heightUsed, parent)
            parent.onMeasureChild(child,
                    parentWidthMeasureSpec, widthUsed,
                    parentHeightMeasureSpec, heightUsed+head.measuredHeight)
            return true;
        }

        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }




    /**
     * 测量获取某个子控件的Size(包括margin)
     *
     * @param child
     * @param parentWidthMeasureSpec
     * @param parentHeightMeasureSpec
     * @param measuredHeight
     * @return
     */
    fun getChildSize(child: View, parentWidthMeasureSpec: Int, parentHeightMeasureSpec: Int, measuredHeight: Int,parent:CoordinatorLayout): IntArray {
        val lp: CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        var horizontalMargin = 0
        var verticalMargin = 0
        parent.onMeasureChild(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, measuredHeight)
        horizontalMargin = lp.leftMargin + lp.rightMargin
        verticalMargin = lp.topMargin + lp.bottomMargin


        val childWidthWithMargin = child.measuredWidth + horizontalMargin
        val childHeightWithMargin = child.measuredHeight + verticalMargin

        return intArrayOf(childWidthWithMargin, childHeightWithMargin)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        Log.e("onStartNestedScroll", "onStartNestedScroll: ");
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL !== 0
    }


    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.e("onNestedPreScroll", "onNestedPreScroll: $dy");
        val head = coordinatorLayout.findViewById<ConstraintLayout>(R.id.cl_head)
        val head_img = coordinatorLayout.findViewById<ConstraintLayout>(R.id.head_img)

       val max_y= head_img.measuredHeight - head.measuredHeight
        val transitionY = child.translationY
        
        if(dy>0){//向上滑
            if(-transitionY>=max_y){//达到最大偏移量
                child.translationY = (-max_y).toFloat()
            }else{
                child.translationY = child.translationY-dy
                consumed[1] = dy
            }
        }
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        Log.e("onNestedScroll", "onNestedScroll: $dyUnconsumed");
        val head = coordinatorLayout.findViewById<ConstraintLayout>(R.id.cl_head)
        val head_img = coordinatorLayout.findViewById<ConstraintLayout>(R.id.head_img)
        val max_y= head_img.measuredHeight - head.measuredHeight
        val transitionY = child.translationY
        if(dyUnconsumed<0){//向下拉到顶
            if(transitionY<0){
                child.translationY = child.translationY-dyUnconsumed
            }else{
                child.translationY = 0f
            }
        }
    }

    

}