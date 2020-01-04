package com.example.yangchaoming.bappdemo.utils

import android.content.Context
import android.util.TypedValue

class CommonUtil {
    companion object{
        fun dp2px(value: Float,context: Context):Int{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics).toInt()
        }
    }
}