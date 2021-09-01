package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2.indicator

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.yangchaoming.bappdemo.R
import kotlin.math.max

class CustomImageIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defaultStyle:Int = 0)
    :ViewGroup(context,attrs,defaultStyle),PageIndicator {
    val TAG = "ImagePageIndicator"
    private var mViewPager: ViewPager2?= null

    var imageRects: ArrayList<Rect> = ArrayList<Rect>() //imageview 坐标

    private var listener: ViewPager2.OnPageChangeCallback?= null

    private var mScrollState:Int = 0

    private var mCurrentPage:Int = -1
    private var mPageOffset:Float = 0f

    var imageViewW:Int = context.resources.getDimensionPixelOffset(R.dimen.pageMargin)*2
    var imageViewH:Int = context.resources.getDimensionPixelOffset(R.dimen.pageMargin)*2

    var viewWidth = 0
    var viewHeight = 0

    var imageViewList = ArrayList<ImageView>()
    var textViewList =  ArrayList<TextView>()

    var textMarginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4f,context.resources.displayMetrics)

    var paddingLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,14f,context.resources.displayMetrics)
    var paddingRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,14f,context.resources.displayMetrics)

    var contentWidth = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e(TAG, "onSizeChanged: ");
        viewWidth = w
        viewHeight = h

         contentWidth = viewWidth - paddingLeft - paddingRight
        if(contentWidth<0)contentWidth =0f
        notifyDataSetChanged()
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount){
            val childAt = getChildAt(i)
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec)
        }
        val measurement_w = MeasureUtils.getMeasurement(widthMeasureSpec,0)
        val measurement_h = MeasureUtils.getMeasurement(heightMeasureSpec, 0)

//        val size = MeasureSpec.getSize(heightMeasureSpec)
//        Log.e(TAG, "onMeasure:11111height=  $size" )

        Log.e("onMeasure11111", "onMeasure: $measurement_w  ---- $measurement_h");
        setMeasuredDimension(measurement_w, max(imageViewH,measurement_h))

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.e("onLayout", "onLayout: 1111111----$top  -----$left----- $bottom");
        val leftMoved = (mPageOffset * viewWidth).toInt() //向左移动了多少
        for (i in imageRects.indices){
            val rect = imageRects[i]
            val imageView = imageViewList[i]


            val tv = textViewList[i]
            val measuredHeight = tv.measuredHeight
            val measuredWidth = tv.measuredWidth
            val tv_left = rect.right+textMarginLeft.toInt()
            val tv_top = (rect.bottom - measuredHeight) / 2
            val tv_right = tv_left + measuredWidth
            val tv_bottom = tv_top + measuredHeight

            imageView.layout(rect.left,rect.top,rect.right,rect.bottom)
            tv.layout(tv_left, tv_top,tv_right,tv_bottom)

//            val initLeft = (viewWidth - (0.6f * imageViewW * (imageRects.size - 1 - i) + imageViewW)).toInt()
            if(i ==  mCurrentPage){
                tv.visibility = View.VISIBLE
            }else if(i == mCurrentPage+1){
                val initLeft = (contentWidth - (0.6f * imageViewW * (imageRects.size - 1 - i) + imageViewW)).toInt() + paddingLeft.toInt()
                if(rect.left == initLeft){
                    tv.visibility = View.INVISIBLE
                }else{
                    tv.visibility = View.VISIBLE
                }
            }else{
                tv.visibility = View.INVISIBLE
            }
        }


    }


    override fun setViewPager(view: ViewPager2) {
        if(mViewPager == view){
            return
        }
        if(mViewPager!=null){
            mViewPager?.unregisterOnPageChangeCallback(viewPager2CallBack)
        }
        if(view.adapter == null){
            return
        }
        mViewPager = view
        mViewPager?.registerOnPageChangeCallback(viewPager2CallBack)
        notifyDataSetChanged()
    }




    val viewPager2CallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            Log.e(TAG, "onPageScrolled: postion = $position , positionOffset = $positionOffset , positionOffsetPixels =  $positionOffsetPixels")
            mCurrentPage = position;
            mPageOffset = positionOffset;

            //计算imageview的位置
            computeImageViewPosition()

            requestLayout()
            listener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            if(mScrollState == ViewPager2.SCROLL_STATE_IDLE){
                mCurrentPage = position
                requestLayout()
            }
            listener?.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            mScrollState = state
            listener?.onPageScrollStateChanged(state)
        }
    }

    private fun computeImageViewPosition() {


        val leftMoved = (mPageOffset * contentWidth).toInt() //向左移动了多少
        for (i in 0 until imageRects.size) {
            val initLeft = (contentWidth - (0.6f * imageViewW * (imageRects.size - 1 - i) + imageViewW)).toInt() + paddingLeft.toInt()


            val rect = imageRects[i]
            var rectLeft = paddingLeft.toInt()
            when {
                i < mCurrentPage -> {
                    rectLeft = -imageViewW
                }
                i == mCurrentPage -> {
                    rectLeft -= leftMoved //初始位置

                }
                i == mCurrentPage+1 -> {

                    val diff = leftMoved + initLeft - viewWidth
                    if(diff>0){
//                        rectLeft = if(leftMoved - initLeft>0) leftMoved - initLeft  else 0
                        rectLeft=  contentWidth.toInt() -leftMoved
                        if(rectLeft<paddingLeft.toInt()) rectLeft = paddingLeft.toInt()
                    }else{
                        rectLeft = initLeft
                    }
                }
                else -> {
                    rectLeft = initLeft
                }
            }

            rect.apply {
                left = rectLeft
                right = left + imageViewW
            }
        }
    }


    override fun setViewPager(view: ViewPager2, initialPosition: Int) {
        setViewPager(view)
        setCurrentItem(initialPosition)
    }

    override fun setCurrentItem(item: Int) {
        mViewPager?.currentItem = item
//        mCurrentPage = item
//        requestLayout()
    }

    override fun setOnPageChangeListener(listener: ViewPager2.OnPageChangeCallback?) {
        this.listener = listener
    }

    override fun notifyDataSetChanged() {
        Log.e(TAG, "notifyDataSetChanged: $viewWidth");
        if(viewWidth == 0 )return
        val adapter = mViewPager?.adapter
        val itemCount = adapter?.itemCount ?: return

        clear()

        for(i in 0 until itemCount){
            val imageView = ImageView(context)
            val layoutParams :ViewGroup.LayoutParams= ViewGroup.LayoutParams(imageViewW, imageViewH)
            imageView.setImageResource(R.mipmap.ic_launcher_round)
            imageView.layoutParams = layoutParams
            imageView.elevation = ((itemCount-i)*0.1f+1)
            addView(imageView)

            imageViewList.add(imageView)

            val tv = TextView(context)
            val lpText :ViewGroup.LayoutParams= ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            tv.layoutParams = lpText
            tv.setTextColor(ContextCompat.getColor(context,R.color.color_bg_main1))
            tv.textSize = 18f
            tv.text="abc #$i"
            tv.elevation = 1f
//            tv.measure(0, 0);
            addView(tv)

            textViewList.add(tv)

            if(i == 0){
                val left =0+paddingLeft.toInt()
                imageRects.add(
                        Rect(left,
                                0,
                                left+imageViewW,
                                imageViewW))

            }else{
                val left = (contentWidth - (0.6f * imageViewW * (itemCount - 1 - i) + imageViewW)).toInt() + paddingLeft.toInt()
                imageRects.add(
                        Rect(left,
                                0,
                                left+imageViewW,
                                imageViewW))
            }
        }

    }


    private fun clear(){
        removeAllViews()
        imageRects.clear()
        imageViewList.clear()
        textViewList.clear()
    }


}