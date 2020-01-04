package com.example.yangchaoming.bappdemo.bapp_course.course_detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CourseDetailPagerAdapter(fm:FragmentManager,var list:ArrayList<Fragment>,var titles:ArrayList<String>) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


}