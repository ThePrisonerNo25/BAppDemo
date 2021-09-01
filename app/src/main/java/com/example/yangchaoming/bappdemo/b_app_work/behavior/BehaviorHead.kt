package com.example.yangchaoming.bappdemo.b_app_work.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R

class BehaviorHead @JvmOverloads constructor(val context: Context? = null, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    //    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
//        val head = parent.findViewById<ConstraintLayout>(R.id.cl_head)
//        getChildSize(head, parentWidthMeasureSpec, parentHeightMeasureSpec, heightUsed, parent)
//        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
//    }
    var recyclerViewHeight = 0
    var recyclerViewWidth = 0

    /* Tracking direction of user motion */
    var mScrollingDirection = 0 //滚动方向
    /* Tracking last threshold crossed */
    var mScrollTrigger = 0

    /* Accumulated scroll distance */
    var mScrollDistance = 0

    /* Distance threshold to trigger animation */
    var mScrollThreshold = 50

     var arrowView: View? =null

    companion object{
        const val DIRECTION_UP = 1;
        const val DIRECTION_DOWN = -1;
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
//        Log.e("onLayoutChild", "onLayoutChild: ");
        val head = parent.findViewById<ConstraintLayout>(R.id.cl_head)
        val recycler_view_card = parent.findViewById<RecyclerView>(R.id.recycler_view_card)
        val measuredHeight = recycler_view_card.measuredHeight
        val measuredWidth = recycler_view_card.measuredWidth

        Log.e("onLayoutChild", "onLayoutChild:measuredWidth== $measuredWidth  ----measuredHeight $measuredHeight");
        if (recyclerViewHeight < measuredHeight) recyclerViewHeight = measuredHeight
        if (recyclerViewWidth < measuredWidth) recyclerViewWidth = measuredWidth
        child.layout(0, head.measuredHeight, child.measuredWidth, child.measuredHeight + head.measuredHeight)

        if(arrowView==null)
            arrowView = parent.findViewById<View>(R.id.iv_arrow_head)

        return true
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL !== 0
    }



    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val recycler_view_card = coordinatorLayout.findViewById<RecyclerView>(R.id.recycler_view_card)

        //Determine direction changes here
        if (dy > 0 && mScrollingDirection != DIRECTION_UP  &&  child==target) {
            mScrollingDirection = DIRECTION_UP;
            mScrollDistance = 0;
        } else if (dy < 0 && mScrollingDirection != DIRECTION_DOWN &&  child==target) {
            mScrollingDirection = DIRECTION_DOWN;
            mScrollDistance = 0;
        }

//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {

        //Consumed distance is the actual distance traveled by the scrolling view
        val recycler_view_card = coordinatorLayout.findViewById<RecyclerView>(R.id.recycler_view_card)
        mScrollDistance += dyUnconsumed;
        Log.e("onNestedScroll", "onNestedScroll: $mScrollDistance");
        if (mScrollDistance > mScrollThreshold
                && mScrollTrigger != DIRECTION_UP  &&  child==target) {

            //Hide the target view
            mScrollTrigger = DIRECTION_UP;
            restartAnimator(1.00f,0.00f,recycler_view_card);
        } else if (mScrollDistance < -mScrollThreshold
                && mScrollTrigger != DIRECTION_DOWN  &&  child==target) {
            //Return the target view
            mScrollTrigger = DIRECTION_DOWN;
            restartAnimator(0.00f, 1.00f,recycler_view_card);
        }
    }

    private var mAnimator:ValueAnimator? =null
    fun restartAnimator(startValue: Float, endValue: Float, recyclerViewCard: RecyclerView){

        if (mAnimator != null) {
            mAnimator?.cancel();
            mAnimator = null;
        }
        val layoutParams = recyclerViewCard.layoutParams
//        Log.e("restartAnimator", "restartAnimator: $recyclerViewWidth");

        mAnimator = ValueAnimator
                .ofFloat(startValue, endValue)
                .setDuration(250)
        mAnimator?.addUpdateListener {
            val i = it.animatedValue as Float
            layoutParams.height = (i*recyclerViewHeight).toInt()

            layoutParams.width = (i*recyclerViewWidth).toInt()
            recyclerViewCard.layoutParams = layoutParams

            arrowView?.rotation = 180*i
        }
        mAnimator?.start()
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
    fun getChildSize(child: View, parentWidthMeasureSpec: Int, parentHeightMeasureSpec: Int, measuredHeight: Int, parent: CoordinatorLayout): IntArray {
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
}