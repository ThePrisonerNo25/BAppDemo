package com.example.yangchaoming.bappdemo.bapp_course.course_detail

import androidx.lifecycle.Lifecycle
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseCommentBean
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseDirectoryItemBean
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean

interface CourseDetailContract {
    interface View{
        fun showMessage(msg:String?)
        fun showLoadingDialog(show : Boolean)
        fun getMLifecycle():Lifecycle
//        fun showCourseList(courseList: ArrayList<FitnessCourseBean>)
        fun showTitleList(titles:ArrayList<String>)

        fun showCourseIntroduction()
        fun showCourseDirectory(list:ArrayList<CourseDirectoryItemBean>)
        fun showCourseCommentList(list: ArrayList<CourseCommentBean>)
    }
}