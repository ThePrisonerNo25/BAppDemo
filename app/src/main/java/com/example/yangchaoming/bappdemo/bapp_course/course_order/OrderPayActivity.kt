package com.example.yangchaoming.bappdemo.bapp_course.course_order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yangchaoming.bappdemo.R

class OrderPayActivity :AppCompatActivity(){

    private var  layoutType : Int = COURSE_TYPE

    private lateinit var payFragment:Fragment

    companion object{
        const val LAYOUT_TYPE = "layout_type"
        const val COURSE_TYPE = 0
        const val SIGN_UP_TYPE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_pay)

        initialData()


        payFragment = when(layoutType){
            COURSE_TYPE ->  CourseOrderPayFragment()
            SIGN_UP_TYPE ->  CourseSignUpPayFragment()
            else ->  CourseOrderPayFragment()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, payFragment)
        transaction.commit()

    }

    private fun initialData(){
        val bundleExtra = intent.getBundleExtra(LAYOUT_TYPE)
        layoutType = bundleExtra?.getInt(LAYOUT_TYPE) ?: COURSE_TYPE
    }
}