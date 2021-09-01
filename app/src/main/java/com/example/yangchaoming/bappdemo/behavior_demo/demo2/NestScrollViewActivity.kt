package com.example.yangchaoming.bappdemo.behavior_demo.demo2

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.behavior_demo.demo1.NestAdapter
import kotlinx.android.synthetic.main.acitity_nest_scroll_view.*

class NestScrollViewActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_nest_scroll_view)


        val list = ArrayList<Int>()
        for (i in 0..19) {
            list.add(i)
        }
        val ad= NestAdapter(this,list)
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recycler_view.adapter = ad

        Handler().post {
            iv_0.visibility = View.GONE
        }
    }
}