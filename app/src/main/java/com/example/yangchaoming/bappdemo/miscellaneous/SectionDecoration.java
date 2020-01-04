package com.example.yangchaoming.bappdemo.miscellaneous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";
    private GroupListener mGroupListener;
    private Context context;
    private Paint mGroutPaint;
    private TextPaint mTextPaint;
    private int mGroupHeight=0;

    private int mTextSize=50;


    public SectionDecoration(GroupListener listener) {
        mGroupListener=listener;
        //设置悬浮栏的画笔---mGroutPaint
        mGroutPaint = new Paint();
        mGroutPaint.setAntiAlias(true);
        mGroutPaint.setDither(true);
        mGroutPaint.setColor(Color.YELLOW);

        //设置悬浮栏中文本的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        context=parent.getContext();
        if(mGroupHeight==0)mGroupHeight=dpTopx(44);

        int pos = parent.getChildAdapterPosition(view);
        String groupId = getGroupName(pos);
        if (groupId == null) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
        }

    }


//    @Override
//    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDrawOver(c, parent, state);
//        int childCount = parent.getChildCount();//recyclerview 显示的数量
//        int itemCount = state.getItemCount();//adpter中需要显示的总数量
//
//        int left =parent.getLeft()+parent.getPaddingLeft(); //item 的left
//        int right=left+parent.getWidth()-parent.getPaddingRight();//item 的 right
//
//        String preGroupName;      //标记上一个item对应的Group
//        String currentGroupName = null;       //当前item对应的Group
//        for (int i = 0; i < childCount; i++) {//遍历正在显示的项
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            preGroupName=currentGroupName;
//            currentGroupName=getGroupName(position);
////            Log.e(TAG, "onDrawOver: currentGroupName-------->"+currentGroupName);
//            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
//                continue;
//            int viewBottom = view.getBottom();
//            float firstDecorationBottom = Math.max(mGroupHeight, view.getTop());//top 决定当前顶部第一个悬浮Group的位置
//            if (position + 1 < itemCount) {
//                //获取下个GroupName
//                String nextGroupName = getGroupName(position + 1);
//                //下一组的第一个View接近头部
//                if (!currentGroupName.equals(nextGroupName) && viewBottom < firstDecorationBottom) {
//                    firstDecorationBottom = viewBottom;
//                }
//
//            }
//            //根据firstDecorationBottom绘制group
//            c.drawRect(left, firstDecorationBottom - mGroupHeight, right, firstDecorationBottom, mGroutPaint);
//
//
//            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//            //文字竖直居中显示
//            float baseLine = firstDecorationBottom - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
//            c.drawText(currentGroupName, left +dpTopx(50) , baseLine, mTextPaint);
//        }
//    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();//recyclerview 显示的数量
        int itemCount = state.getItemCount();//adpter中需要显示的总数量

        int left =parent.getLeft()+parent.getPaddingLeft(); //item 的left
        int right=left+parent.getWidth()-parent.getPaddingRight();//item 的 right

        String preGroupName;      //标记上一个item对应的Group
        String currentGroupName = null;       //当前item对应的Group
        for (int i = 0; i < childCount; i++) {//遍历正在显示的项
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName=currentGroupName;
            currentGroupName=getGroupName(position);
//            Log.e(TAG, "onDrawOver: currentGroupName-------->"+currentGroupName);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
                continue;
            int viewBottom = view.getBottom();
            int viewTop=view.getTop();

            int firstDecorationBottom=0;
            int padding=dpTopx(10);
            if (position + 1 < itemCount) {
                //获取下个GroupName
                String nextGroupName = getGroupName(position + 1);

//                if(!currentGroupName.equals(nextGroupName)&& viewTop < mGroupHeight){
//                    c.drawRect(left, viewBottom-mGroupHeight, right,viewBottom, mGroutPaint);
//                    firstDecorationBottom=viewBottom;
//
//                }else if(viewTop < mGroupHeight){
//                    c.drawRect(left, 0, right,mGroupHeight, mGroutPaint);
//                    firstDecorationBottom=mGroupHeight;
//                }else {
//                    c.drawRect(left, viewTop-mGroupHeight, right,viewTop, mGroutPaint);//正常情况
//                    firstDecorationBottom=viewTop;
//                }

                if(!currentGroupName.equals(nextGroupName)&&viewBottom <(mGroupHeight-padding)){
                    c.drawRect(left, viewTop+(mGroupHeight-2*padding), right,viewTop, mGroutPaint);
                    Log.e(TAG, "onDrawOver: bbbb" );
                }else if(viewTop<mGroupHeight-padding){
                    c.drawRect(left, 0, right,mGroupHeight-2*padding, mGroutPaint);

                }else {
                    c.drawRect(left, viewTop-mGroupHeight+padding, right,viewTop-padding, mGroutPaint);//正常情况
                }
//                c.drawRect(left, viewTop-mGroupHeight+padding, right,viewTop-padding, mGroutPaint);//正常情况

            }

//            if(!currentGroupName.equals(nextGroupName)&& viewTop < mGroupHeight-padding&&viewTop>mGroupHeight-2*padding){
//                c.drawRect(left, 0, right,mGroupHeight-2*padding, mGroutPaint);
//                Log.e(TAG, "onDrawOver: aaaaa" );
//            }else

//            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//            //文字竖直居中显示
//            float baseLine = firstDecorationBottom - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
//            c.drawText(currentGroupName, left +dpTopx(50) , baseLine, mTextPaint);
        }
    }


    public String getGroupName(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(position);
        } else {
            return null;
        }
    }

    //判断是不是组中的第一个位置
//根据前一个组名，判断当前是否为新的组
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = getGroupName(pos - 1);
            String groupId = getGroupName(pos);
            return !TextUtils.equals(prevGroupId, groupId);
        }
    }

    private  int dpTopx(int dp){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
