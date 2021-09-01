package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_viewpager2.*
import java.io.File

const val TAG = "ViewPagerActivity"
class ViewPagerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager2)

//        val demoCollectionAdapter = DemoCollectionAdapter(this)
        val demoCollectionAdapter = DemoCollectionAdapter2(this, ArrayList())
        pager.adapter = demoCollectionAdapter

        pager.offscreenPageLimit = 3
        pager.registerOnPageChangeCallback(value)
        pager.setPageTransformer(ViewPager2PageTransformation())


        indicator.setViewPager(pager)
        demoCollectionAdapter.resetList(arrayListOf(1,2,3,4,5,6))
        indicator.notifyDataSetChanged()
        indicator.setViewPager(pager,5)

//        val file = File(getExternalFilesDir(
//                Environment.DIRECTORY_PICTURES), "albumName")
//        if (!file.mkdirs()) {
//            Log.e(LOG_TAG, "Directory not created")
//        }

    }

    val value = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            Log.e(TAG, "onPageScrolled: postion = $position , positionOffset = $positionOffset , positionOffsetPixels =  $positionOffsetPixels")
        }
    }

    override fun onDestroy() {
        pager.unregisterOnPageChangeCallback(value)
        super.onDestroy()
    }

}