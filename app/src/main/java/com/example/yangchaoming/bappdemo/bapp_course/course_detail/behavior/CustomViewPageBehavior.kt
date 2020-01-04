package com.example.yangchaoming.bappdemo.bapp_course.course_detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.yangchaoming.bappdemo.R


class CustomViewPageBehavior @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {

        val childLpHeight = child.layoutParams.height
        if(childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT || childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT){

            val headImg = parent.findViewById<ImageView>(R.id.video_view)
            val nsMiddle = parent.findViewById<NestedScrollView>(R.id.ns_middle)
            nsMiddle?.measure(nsMiddle.layoutParams.width, nsMiddle.layoutParams.height)
            headImg?.measure(headImg.layoutParams.width, headImg.layoutParams.height)
            Log.e("onMeasureChild", "onMeasureChild: ${nsMiddle.height} ---- ${headImg.height}");
            parent.onMeasureChild(child,
                    parentWidthMeasureSpec, widthUsed,
                    parentHeightMeasureSpec, heightUsed+nsMiddle.height+headImg.height);

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
        val lp:ViewGroup.MarginLayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        var horizontalMargin = 0
        var verticalMargin = 0
        parent.onMeasureChild(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, measuredHeight)
        horizontalMargin = lp.leftMargin + lp.rightMargin
        verticalMargin = lp.topMargin + lp.bottomMargin

        val childWidthWithMargin = child.measuredWidth + horizontalMargin
        val childHeightWithMargin = child.measuredHeight + verticalMargin

        return intArrayOf(childWidthWithMargin, childHeightWithMargin)
    }


    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.ns_middle
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

       val dependBottom = dependency.bottom;
        val transitionY = dependBottom+dependency.translationY
//        Log.e("onDependentViewChanged", "onDependentViewChanged: $dependBottom transitionY= $transitionY");
        child.y = transitionY
        return true
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}