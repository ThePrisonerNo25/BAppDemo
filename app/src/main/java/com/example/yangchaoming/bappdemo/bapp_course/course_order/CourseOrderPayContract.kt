package com.example.yangchaoming.bappdemo.bapp_course.course_order

import androidx.lifecycle.Lifecycle
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean

interface CourseOrderPayContract {
    interface View{
        fun showMessage(msg:String?)
        fun showLoadingDialog(show : Boolean)
        fun getMLifecycle():Lifecycle
        fun showOrderDetail()

    }
}