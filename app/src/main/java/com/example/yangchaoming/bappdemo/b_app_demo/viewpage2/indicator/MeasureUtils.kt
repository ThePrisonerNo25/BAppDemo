package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2.indicator

import android.view.View
import kotlin.math.min

class MeasureUtils {
    companion object {
        fun getMeasurement(measureSpec: Int, contentSize: Int): Int {
            val specMode = View.MeasureSpec.getMode(measureSpec)
            val specSize = View.MeasureSpec.getSize(measureSpec)
            var resultSize = 0;
            when (specMode) {
                View.MeasureSpec.UNSPECIFIED ->
                    resultSize = contentSize
                View.MeasureSpec.AT_MOST -> {
                    resultSize = min(specSize,contentSize)
                }
                View.MeasureSpec.EXACTLY -> {
                    resultSize = specSize
                }
            }

            return resultSize
        }
    }
}