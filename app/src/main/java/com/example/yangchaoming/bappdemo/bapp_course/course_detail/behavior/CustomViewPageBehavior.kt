package com.example.yangchaoming.bappdemo.bapp_course.course_detail.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.utils.CommonUtil
import com.google.android.material.tabs.TabLayout


class CustomViewPageBehavior @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context,attrs){

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        Log.e("onMeasureChild", "onMeasureChild: 22222222222222");
        val childLpHeight = child.layoutParams.height
        if(childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT || childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT){

            val headImg = parent.findViewById<ImageView>(R.id.video_view)
            val ns_middle = parent.findViewById<NestedScrollView>(R.id.ns_middle)
            val tabLayout = parent.findViewById<TabLayout>(R.id.tab_layout)
            val btn_add = parent.findViewById<Button>(R.id.btn_add)

            getChildSize(headImg, parentWidthMeasureSpec, parentHeightMeasureSpec, heightUsed, parent)
            getChildSize(ns_middle, parentWidthMeasureSpec, parentHeightMeasureSpec, heightUsed, parent)
             getChildSize(btn_add, parentWidthMeasureSpec, parentHeightMeasureSpec, heightUsed, parent)
//
            Log.e("onMeasureChild", "onMeasureChild: ${headImg.measuredHeight} ---- ${tabLayout.measuredHeight}---${btn_add.measuredHeight}");
//            Log.e("onMeasureChild", "onMeasureChild: 11111111111111111111");
            parent.onMeasureChild(child,
                    parentWidthMeasureSpec, widthUsed,
                    parentHeightMeasureSpec, heightUsed+headImg.measuredHeight+tabLayout.measuredHeight + btn_add.measuredHeight);
//            Log.e("onMeasureChild", "onMeasureChild: $heightUsed ==== ${CommonUtil.dp2px(310f,parent.context)}");
//            parent.onMeasureChild(child,
//                    parentWidthMeasureSpec, widthUsed,
//                    parentHeightMeasureSpec, heightUsed+CommonUtil.dp2px(310f,parent.context));

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