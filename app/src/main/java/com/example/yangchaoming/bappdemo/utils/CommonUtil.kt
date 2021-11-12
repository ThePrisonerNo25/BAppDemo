package com.example.yangchaoming.bappdemo.utils

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat

class CommonUtil {
    companion object{
        fun dp2px(value: Float,context: Context):Int{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics).toInt()
        }

        fun date2TimeStamp(date: String?, format: String?): Long? {
            try {
                val sdf = SimpleDateFormat(format)
                return sdf.parse(date).time / 1000
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}