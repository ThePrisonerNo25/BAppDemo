package com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.Obj
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.CourseDetailActivity
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.CourseDetailPresenter
import java.util.*

class CourseIntroductionFragment : Fragment() {
    
//    lateinit var presenter: Obj

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = (activity as CourseDetailActivity).id
        Log.e("onCreate", "onCreate: CourseIntroductionFragment: id : $id");
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_brief, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("onViewCreated", "onViewCreated: CourseIntroductionFragment");

//        presenter = Obj("CourseIntroductionFragment")
//        Log.e("onViewCreated", "onViewCreated: ${presenter.hashCode()}");
    }




}