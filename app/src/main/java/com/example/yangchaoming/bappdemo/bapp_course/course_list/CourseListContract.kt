package com.example.yangchaoming.bappdemo.bapp_course.course_list

import androidx.lifecycle.Lifecycle
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean

interface CourseListContract {
    interface View{
        fun showMessage(msg:String?)
        fun showLoadingDialog(show : Boolean)
        fun getMLifecycle():Lifecycle
        fun showCourseList(courseList: ArrayList<FitnessCourseBean>)

    }
}