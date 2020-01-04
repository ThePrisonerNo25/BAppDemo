package com.example.yangchaoming.bappdemo.miscellaneous;

import android.content.Context;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentPagerAdaper  extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment>  mFragments;
    private List<String> mTitles;

    public MyFragmentPagerAdaper(FragmentManager fm, Context context, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mContext=context;
        mFragments=fragments;
        mTitles=titles;
    }

    @Override
    public Fragment getItem(int i) {

        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return  mTitles.get(position);
    }
}
