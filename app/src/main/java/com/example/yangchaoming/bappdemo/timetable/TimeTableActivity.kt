package com.example.yangchaoming.bappdemo.timetable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.islandparadise14.mintable.model.ScheduleDay
import com.islandparadise14.mintable.model.ScheduleEntity
import kotlinx.android.synthetic.main.acitity_time_table.*

class TimeTableActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_time_table)
//        val day = arrayOf("Mon", "Tue", "Wen", "Thu", "Fri","Sat","Sun")
//
//        val scheduleList: ArrayList<ScheduleEntity> = ArrayList()
//        val schedule = ScheduleEntity(
//                32, //originId
//                "Database", //scheduleName
//                "IT Building 301", //roomInfo
//                ScheduleDay.TUESDAY, //ScheduleDay object (MONDAY ~ SUNDAY)
//                "8:20", //startTime format: "HH:mm"
//                "10:30", //endTime  format: "HH:mm"
//                "#73fcae68", //backgroundColor (optional)
//                "#000000" //textcolor (optional)
//        )
//
//        scheduleList.add(schedule)
//
//        table.initTable(day)
//        table.updateSchedules(scheduleList)


    }


}