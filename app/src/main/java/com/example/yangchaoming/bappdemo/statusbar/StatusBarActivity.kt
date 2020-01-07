package com.example.yangchaoming.bappdemo.statusbar

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_statusbar.*

class StatusBarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statusbar)
//        window.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
////            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            statusBarColor = Color.TRANSPARENT
////            statusBarColor =resources.getColor(R.color.colorPrimary)
//        }

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        btn_attention.setOnClickListener {
            it.isSelected = !it.isSelected
        }
    }
}