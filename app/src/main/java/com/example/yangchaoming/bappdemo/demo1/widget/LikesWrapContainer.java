package com.example.yangchaoming.bappdemo.demo1.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yangchaoming.bappdemo.R;

import java.util.ArrayList;
import java.util.List;

public class LikesWrapContainer extends ViewGroup implements ViewGroup.OnHierarchyChangeListener{
    private static final String TAG = "LikesWrapContainer";
    //自定义属性
    private int mMaxLines = Integer.MAX_VALUE;//默认不限制行数
    private int mHorizontalSpace = 0;//横向的间距
    private int mVerticalSpace = 0;//纵向的间距

    private List<Integer> childCountInLines = new ArrayList<>();
    private List<Integer> heightInLines = new ArrayList<>();

    private boolean isMaxLins = false;//是否已到达最大限制行数

    private List<String> datas;

    private final SimpleWeakObjectPool<View>  COMMENT_TEXT_POOL =new  SimpleWeakObjectPool<>();

    public LikesWrapContainer(Context context) {
        this(context, null);
    }

    public LikesWrapContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.LineWrapContainer);
            mMaxLines = t.getInteger(R.styleable.LineWrapContainer_maxLines, Integer.MAX_VALUE);
            mHorizontalSpace = t.getDimensionPixelSize(R.styleable.LineWrapContainer_horizontalSpace, 0);
            mVerticalSpace = t.getDimensionPixelSize(R.styleable.LineWrapContainer_verticalSpace, 0);
            t.recycle();
        }
        setWillNotDraw(false);
        setOnHierarchyChangeListener(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        isMaxLins = false;
        childCountInLines.clear();
        heightInLines.clear();

        //本控件最终measure的高
        int measuredHeight = 0;

        int maxSizeOneLine = width - getPaddingLeft() - getPaddingRight();
        int lineHasUsedWidth = 0;
        int maxChildHeightOneLine = 0;
        int childCountOneLine = 0;

        for (int i = 0; i < getChildCount(); i++) {
            if (isMaxLins) {
                break;
            }
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int[] childSize = getChildSize(child, widthMeasureSpec, heightMeasureSpec, measuredHeight);
            int childWidthWithMargin = childSize[0];
            int childHeightWithMargin = childSize[1];

            if (lineHasUsedWidth + childWidthWithMargin < maxSizeOneLine) {//未超出一行
                lineHasUsedWidth += childWidthWithMargin + mHorizontalSpace;
                maxChildHeightOneLine = Math.max(childHeightWithMargin, maxChildHeightOneLine);
                childCountOneLine += 1;

            } else {//超过一行则折行
                childCountInLines.add(childCountOneLine);
                heightInLines.add(maxChildHeightOneLine);

                if (childCountInLines.size() <= mMaxLines) {
                    measuredHeight += maxChildHeightOneLine;
                    if (childCountInLines.size() == mMaxLines) {
                        isMaxLins = true;
                    }
                } else {
                    isMaxLins = true;
                }

                //新起一行，重置参数
                lineHasUsedWidth = childWidthWithMargin + mHorizontalSpace;
                childCountOneLine = 1;
                maxChildHeightOneLine = childHeightWithMargin;
            }

        }


        if (!isMaxLins) {
            childCountInLines.add(childCountOneLine);
            heightInLines.add(maxChildHeightOneLine);

            measuredHeight += maxChildHeightOneLine;
//            Log.e(TAG, "onMeasure: measuredHeight"+measuredHeight );
        }

        //最后为measureHeight加上padding和行间距
        measuredHeight += getPaddingTop() + getPaddingBottom();
        for (int k = 0; k < childCountInLines.size() - 1; k++) {
            measuredHeight += mVerticalSpace;
        }

        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if(mode==MeasureSpec.EXACTLY){
            measuredHeight=height;
        }
//        measuredHeight = Math.min(measuredHeight, height);

//        Log.e(TAG, "onMeasure: measuredHeight"+height );

        setMeasuredDimension(width, measuredHeight);
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
    private int[] getChildSize(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec, int measuredHeight) {
        LayoutParams lp = child.getLayoutParams();
        int horizontalMargin = 0, verticalMargin = 0;
        if (lp instanceof MarginLayoutParams) {
            measureChildWithMargins(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, measuredHeight);
            MarginLayoutParams mp = (MarginLayoutParams) lp;
            horizontalMargin = mp.leftMargin + mp.rightMargin;
            verticalMargin = mp.topMargin + mp.bottomMargin;
        } else {
            measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        }
        int childWidthWithMargin = child.getMeasuredWidth() + horizontalMargin;
        int childHeightWithMargin = child.getMeasuredHeight() + verticalMargin;
//        Log.e(TAG, "getChildSize: child.getMeasuredHeight()"+child.getMeasuredHeight() );
        return new int[]{childWidthWithMargin, childHeightWithMargin};
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //左上角坐标
        int x = getPaddingLeft();
        int y = getPaddingTop();

        int childIndex = 0;

        for (int j = 0; j < childCountInLines.size(); j++) {
            int childCount = childCountInLines.get(j);
            int lineHeight = heightInLines.get(j);

            for (int h = 0; h < childCount; h++) {
                if (childIndex >= getChildCount()) {
                    break;
                }
                View child = getChildAt(childIndex);
                if (child.getVisibility() == GONE) {
                    continue;
                }

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                int leftMargin = 0, rightMargin = 0, topMargin = 0, bottomMargin = 0;
                LayoutParams childlp = child.getLayoutParams();
                if (childlp instanceof MarginLayoutParams) {
                    leftMargin = ((MarginLayoutParams) childlp).leftMargin;
                    rightMargin = ((MarginLayoutParams) childlp).rightMargin;
                    topMargin = ((MarginLayoutParams) childlp).topMargin;
                    bottomMargin = ((MarginLayoutParams) childlp).bottomMargin;
                }

                child.layout(x + leftMargin, y + topMargin, x + leftMargin + childWidth, y + topMargin + childHeight);

                //移动横坐标，重新确定基点X
                x += leftMargin + childWidth + rightMargin + mHorizontalSpace;

                childIndex++;
            }

            //折行时重置x,y基点
            x = getPaddingLeft();
            y += lineHeight + mVerticalSpace;
        }

    }

    public View creatItem(String item,LayoutParams layoutParams,View view){
       if(view==null)  view = new ImageView(getContext());
       if(view instanceof ImageView){
           ((ImageView) view).setImageDrawable(ContextCompat.getDrawable(this.getContext(),R.mipmap.ic_launcher_round));
       }
        view.setLayoutParams(layoutParams);
        return view;
    }

    public void setDatas(List<String> list){

        if(list!=null){
            int oldCount = getChildCount();
            int newCount = list.size();
            if (oldCount > newCount) {
                removeViewsInLayout(newCount, oldCount - newCount);
            }
            LayoutParams layoutParams = new LayoutParams(dp2px(30),dp2px(30));
            for (int i = 0; i < newCount; i++) {
                boolean hasChild = i < oldCount;
                View childView=null;
                if (hasChild)childView= getChildAt(i);
                String item = list.get(i);
                if(childView==null){
                    childView=COMMENT_TEXT_POOL.get();
                    View imageView = creatItem(item, layoutParams,  childView);
                    addView(imageView);
                }else {
                    creatItem(item, layoutParams, childView);
                }
            }
            requestLayout();
        }
    }

  






    public void setMaxLins(int maxLines) {
        mMaxLines = maxLines;
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    public void setHorizontalSpace(int horizontalSpace) {
        this.mHorizontalSpace = horizontalSpace;
    }

    public int getHorizontalSpace() {
        return mHorizontalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.mVerticalSpace = verticalSpace;
    }

    public int getVerticalSpace() {
        return mVerticalSpace;
    }


    private int dp2px(float dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
        COMMENT_TEXT_POOL.put(child);
    }
}
