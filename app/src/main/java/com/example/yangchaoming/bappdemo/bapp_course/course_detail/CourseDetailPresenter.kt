package com.example.yangchaoming.bappdemo.bapp_course.course_detail

import android.content.Context
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseDirectoryItemBean

class CourseDetailPresenter (val context: Context,val view : CourseDetailContract.View){

    var titleList = ArrayList<String>()

    var courseList = ArrayList<CourseDirectoryItemBean>()

    fun mockData(){
        titleList.add("TAB#1")
        titleList.add("TAB#2")
        titleList.add("TAB#3")
        view.showTitleList(titleList)
    }

    fun getCourseList() {
        for (i in 0 until 10){
            val bea = CourseDirectoryItemBean("courseIma_#$i", "courseName_$i", "courseTitle_#$i", "courseDes_#$i", i % 2 == 0, "00:1$i")
            courseList.add(bea)
        }
       view.showCourseDirectory(courseList)
    }




}