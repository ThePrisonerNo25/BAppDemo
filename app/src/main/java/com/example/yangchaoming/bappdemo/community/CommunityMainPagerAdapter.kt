package com.example.yangchaoming.bappdemo.community

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CommunityMainPagerAdapter(var fragmens:ArrayList<Fragment> ,var titles: ArrayList<String>,val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
       return fragmens[position];
    }

    override fun getCount(): Int = fragmens.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}