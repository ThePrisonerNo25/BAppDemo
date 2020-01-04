package com.example.yangchaoming.bappdemo.demo1

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import android.widget.FrameLayout
import com.example.yangchaoming.bappdemo.R

class DynamicCircleActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_circle);
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container);
        val dynamicCircleFragment = DynamicCircleFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, dynamicCircleFragment).commit();
    }
}