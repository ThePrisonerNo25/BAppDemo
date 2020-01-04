package com.example.yangchaoming.bappdemo.bapp_course.course_list

import android.content.Context
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean

class CoursePresenter(val context: Context,val view:CourseListContract.View){
    private var courseList = ArrayList<FitnessCourseBean>()

    fun getList(){
        mockData()
        view.showCourseList(courseList)
    }

    fun mockData(){
        for (i in 0 until 10){
           val bean = FitnessCourseBean("courseId_$i","course_img_$i","$i 有氧舞蹈（AEROBIC DANCE）是配合音乐有节奏舞动的有氧运动，能把许多舞蹈动作健美操化，反复进行组合练习。"
            ,i%2,"1288"+"${i%9}","courseName_$i","97"+"${i%6}","courseTitle_$i",i%3,i%2)
            courseList.add(bean)
        }
    }
}