package com.example.yangchaoming.bappdemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.yangchaoming.bappdemo.R;

import java.util.List;

/**
 * @author KCrason
 * @date 2018/4/27
 */
public class NineImageAdapter implements NineGridView.NineGridAdapter<String> {

    private List<String> mImageBeans;

    private Context mContext;

    private RequestOptions mRequestOptions;

    private DrawableTransitionOptions mDrawableTransitionOptions;


    public NineImageAdapter(Context context, RequestOptions requestOptions, DrawableTransitionOptions drawableTransitionOptions, List<String> imageBeans) {
        this.mContext = context;
        this.mDrawableTransitionOptions = drawableTransitionOptions;
        this.mImageBeans = imageBeans;
        int itemSize = (getScreenWidth() - 2 * dp2px(4) - dp2px(54)) / 3;
        this.mRequestOptions = requestOptions.override(itemSize, itemSize);
    }

    @Override
    public int getCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    @Override
    public String getItem(int position) {
        return mImageBeans == null ? null :
                position < mImageBeans.size() ? mImageBeans.get(position) : null;
    }

    @Override
    public View getView(int position, View itemView) {
        ImageView imageView;
        if (itemView == null) {
            imageView = new ImageView(mContext);
            imageView.setBackgroundColor(Color.parseColor("#f2f2f2"));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            imageView = (ImageView) itemView;
        }
//        String url = mImageBeans.get(position);
//        Glide.with(mContext).load(url).apply(mRequestOptions).transition(mDrawableTransitionOptions).into(imageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
        return imageView;
    }

    public  int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, mContext.getResources().getDisplayMetrics());
    }

    public  int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public  int getScreenHeight(){
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }
}
