package com.example.yangchaoming.bappdemo.popupwindow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_popupwind.*
import org.json.JSONArray
import org.json.JSONObject
import com.alibaba.fastjson.JSON




class MainActivity : AppCompatActivity(){
//    var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popupwind)
        tv_popup_window.setOnClickListener {
            val popUpClass = PopUpClass()
            popUpClass.showPopupWindow(it,this@MainActivity)
        }

        tv_popup_window.append("fdadfasdf")


    }
}