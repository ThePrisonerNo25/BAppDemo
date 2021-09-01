package com.example.yangchaoming.bappdemo.b_app_demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.b_app_demo.bean.LessonRingData
import kotlinx.android.synthetic.main.acitity_de.*
import java.lang.String

class DeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_de)

        battery.setBatteryFraction(0.8f)

        circle_loading.setProgressValue(100)

        circle_progress.setProgressValue(50)



        btn_lesson_ring.setOnClickListener {
//            val list = arrayListOf<LessonRingData>(LessonRingData(10f, "#D81B60"),
//                    LessonRingData(12f, "#eab807"),
//                    LessonRingData(13f, "#6a8136"),
//                    LessonRingData(0f, "#21b8c5")
////                    LessonRingData(40f, R.color.color_selectbtn_normal)
//            )

            val color = ContextCompat.getColor(this, R.color.colorAccent)
            val hexColor = String.format("#%06X", 0xFFFFFF and color)
            Log.e("TAG", "onCreate: $color -------- hexColor: $hexColor")
            val list = arrayListOf<LessonRingData>(LessonRingData(10f, "#D81B60"),
                    LessonRingData(12f, "#eab807"),
                    LessonRingData(13f, "#6a8136"),
                    LessonRingData(0f, "#21b8c5")
//                    LessonRingData(40f, R.color.color_selectbtn_normal)
            )
            lessonRingView.setData(list)
        }


        weight_progress.setProgressAndValue(0.3f, "50kg")

        weight_history.setProgressAndValue(0.3f, "50kg")
    }

}