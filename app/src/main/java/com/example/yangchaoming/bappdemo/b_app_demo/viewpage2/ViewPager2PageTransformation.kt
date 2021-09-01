package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.yangchaoming.bappdemo.R
import kotlin.math.abs

class ViewPager2PageTransformation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        Log.e("transformPage", "transformPage:position =  $position");
//        val absPos = abs(position)
//        page.apply {
//            translationY = absPos * 500f
////            translationX = absPos * 500f
//            scaleX = 1f
//            scaleY = 1f
//        }
//        when {
//            position < -1 ->
//                page.alpha = 0.1f
//            position <= 1 -> {
//                page.alpha = Math.max(0.2f, 1 - Math.abs(position))
//            }
//            else -> page.alpha = 0.1f
//        }

        val pageMarginPx =  page.context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx =  page.context.resources.getDimensionPixelOffset(R.dimen.offset)

        val viewPager = page.parent.parent as ViewPager2
//        val offset = position * -(1 * offsetPx + pageMarginPx)
        val offset = position * -(2*pageMarginPx)
        if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }
}