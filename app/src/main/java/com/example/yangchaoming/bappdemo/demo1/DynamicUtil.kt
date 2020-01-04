package com.example.yangchaoming.bappdemo.demo1

import android.content.Context
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DynamicUtil {
//    const val value="static value"
    fun getFormatDay( dateString: String):String{
        var formartDay=dateString
        if(TextUtils.isEmpty(dateString)||dateString.length<10) return dateString
        val data= Date()
        val today = Calendar.getInstance()
        today.time=data
        val todayString = today.time.getStringTimeStampWithDate("yyyy-MM-dd")

        val yesterday = Calendar.getInstance()
        yesterday.time=data
        yesterday.add(Calendar.DAY_OF_MONTH,-1)
        val yesterdayString = yesterday.time.getStringTimeStampWithDate("yyyy-MM-dd")


        val split = dateString.split(" ")
        val split0 = split[0]
        val split1 = if(split.size>1) split[1] else ""

        if(todayString== split0){
//            val dateWithServerTimeStamp = dateString.getDateWithServerTimeStamp("yyyy-MM-dd HH:mm")
//            val dateFormat = SimpleDateFormat("KK:mm")
//            val format = dateFormat.format(dateWithServerTimeStamp)
//
//            Log.e(tag,format)
//
//            val date = Calendar.getInstance()
//            date.time=dateWithServerTimeStamp;
//            val am_pm = date.get(Calendar.AM_PM)
//            when(am_pm){
//                0 -> formartDay="上午"+format
//                1 -> formartDay="下午"+format
//            }
            formartDay="今天"+ split1

        }else if(yesterdayString== split0){
            formartDay="昨天"+ split1
        }

        return formartDay
    }

    /** Converting from String to Date **/
    fun String.getDateWithServerTimeStamp(pattern:String): Date? {
        val dateFormat = SimpleDateFormat(pattern,
                Locale.getDefault())
//        dateFormat.timeZone = TimeZone.getTimeZone("GMT")  // IMP !!!
        dateFormat.timeZone = TimeZone.getDefault()  // IMP !!!
        try {
            return dateFormat.parse(this)
        } catch (e: ParseException) {
            return null
        }
    }

    /** Converting from Date to String**/
    fun Date.getStringTimeStampWithDate(pattern:String): String {
        val dateFormat = SimpleDateFormat(pattern,
                Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(this)
    }


    fun calcStatusBarHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }

}