package com.example.yangchaoming.bappdemo.statusbar

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.yangchaoming.bappdemo.GlideApp
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicUtil.getStringTimeStampWithDate
import com.example.yangchaoming.bappdemo.widget.IconDrawable
import com.example.yangchaoming.bappdemo.widget.RotateAddIcon
import kotlinx.android.synthetic.main.acitity_glide_demo.*
import kotlinx.android.synthetic.main.activity_statusbar.*
import java.text.SimpleDateFormat
import java.util.*

class StatusBarActivity : AppCompatActivity(){
    companion object{
        const val TAG = "StatusBarActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeColor = ThemeStorage.getThemeColor(this)
        Log.e(TAG, "onCreate: themeColor=="+themeColor )
//        ThemeStorage.setThemeColor(this,themeColor)

        ThemeManager.setCustomizedThemes(this,themeColor)
        setContentView(R.layout.activity_statusbar)

//        imageView.setOnClickListener {
//            if(themeColor == "GreenDarkTheme"){
//                ThemeStorage.setThemeColor(this,"GreenLightTheme")
//                recreate()
//            }else{
//                ThemeStorage.setThemeColor(this,"GreenDarkTheme")
//                recreate()
//            }
//        }

        theme1.setOnClickListener {
            if(themeColor != "GreenDarkTheme"){
                ThemeStorage.setThemeColor(this,"GreenDarkTheme")
                recreate()
            }

        }

        theme2.setOnClickListener {
            if(themeColor != "GreenLightTheme"){
                ThemeStorage.setThemeColor(this,"GreenLightTheme")
                recreate()
            }
        }

        theme3.setOnClickListener {
            if(themeColor != "BlueDarkTheme"){
                ThemeStorage.setThemeColor(this,"BlueDarkTheme")
                recreate()
            }
        }

        enableBtn.setOnClickListener {
            btn_big.isEnabled = !btn_big.isEnabled
            btn_middle.isEnabled = !btn_middle.isEnabled
            btn_middle1.isEnabled = !btn_middle1.isEnabled
            btn_small.isEnabled = !btn_small.isEnabled
        }


//        window.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
////            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            statusBarColor = Color.TRANSPARENT
////            statusBarColor =resources.getColor(R.color.colorPrimary)
//        }

        window.apply {
//            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.RED
        }

        btn_attention.setOnClickListener {
//            it.isSelected = !it.isSelected
            if(rotate_view.rotateStyle == RotateAddIcon.LIGHT){
                rotate_view.setStyle(RotateAddIcon.DARK)
                val color = ContextCompat.getColor(this@StatusBarActivity, R.color.white)
                rotate_view.setBackgroundColor(color)
            }else{
                rotate_view.setStyle(RotateAddIcon.LIGHT)
                val color = ContextCompat.getColor(this@StatusBarActivity, R.color.color_bg_main1)
                rotate_view.setBackgroundColor(color)
            }

        }

//
//        val drawable = IconDrawable(
//                ContextCompat.getDrawable(this,R.drawable.vegetarian_symbol)!!,
//                ContextCompat.getColor(this,android.R.color.holo_orange_dark),
//                this)
//        imageView.setImageDrawable(drawable)

        GlideApp.with(this).load("")
//                .placeholder(R.mipmap.girl1)
                //                .placeholder(R.mipmap.girl1)
                .error(R.mipmap.girl1)
                .transform(MultiTransformation(CenterCrop(), GranularRoundedCorners(30f, 30f, 0f, 0f)))
                //                .fallback(R.mipmap.ic_launcher)
                .into(imageView)

        val calendar = Calendar.getInstance()
        val data = calendar.get(Calendar.DATE)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val calendar1 = Calendar.getInstance()
//        calendar1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0)
        calendar1.set(calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH),0,0,0)
        val data1 = calendar1.get(Calendar.DATE)
        val hour1 = calendar1.get(Calendar.HOUR_OF_DAY)
        val minute1 = calendar1.get(Calendar.MINUTE)

        val calendar2 = Calendar.getInstance()
        calendar2.set(Calendar.HOUR_OF_DAY,0)
        calendar2.set(Calendar.MINUTE,23)
        calendar2.set(Calendar.SECOND,0)


//        Log.e(TAG, "onCreate: calendar.time =${calendar.time.time} ---- calendar1 = ${calendar1.time.time}" )
//        Log.e(TAG, "onCreate: hour =${hour} ---- hour1 = ${hour1}" )
//        Log.e(TAG, "onCreate: minute =${minute} ---- minute1 = ${minute1}" )

        val format =  SimpleDateFormat("yyyy MM dd")
        val format1 =  SimpleDateFormat("yyyy MM dd HH:mm:ss")
//        Log.e(TAG, "onCreate: calendar1 = ${format.format(calendar1.timeInMillis)} ------ calendar =  ${format.format(calendar.timeInMillis)}" )
//        Log.e(TAG, "onCreate1: calendar1 = ${format1.format(calendar1.timeInMillis)} ----- calendar =  ${format1.format(calendar.timeInMillis)} " )
        Log.e(TAG, "onCreate1:  ----- calendar =  ${format1.format(calendar2.timeInMillis)} ---timeInMillis = ${calendar2.timeInMillis}" )

//        Log.e(TAG, "onCreate: data = $data  ------ hour = $hour  --  d2= ${d2.date}" )
//        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
//        val format = simpleDateFormat.format(calendar)
//        calendar.set


//        Log.e(TAG, "onCreate: d2.time = ${d2.time} ---- calendar.time = ${calendar.time.time}"  )


//        val calendar1 = Calendar.getInstance()
//        val data1 = calendar1.get(Calendar.DATE)
//        val hour1 = calendar1.get(Calendar.HOUR_OF_DAY)
//        Log.e(TAG, "onCreate: data =-- $data1  ------ hour =-- $hour1" )
    }
}