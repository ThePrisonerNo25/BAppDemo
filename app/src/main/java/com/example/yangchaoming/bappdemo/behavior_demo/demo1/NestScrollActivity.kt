package com.example.yangchaoming.bappdemo.behavior_demo.demo1

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.yangchaoming.bappdemo.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_nestscroll.*


class NestScrollActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestscroll)

        ViewCompat.setNestedScrollingEnabled(head_img,true)

        head_img.setOnTouchListener { v, event -> run{
            val action = event.action
            event.actionMasked
            when(action){
                MotionEvent.ACTION_DOWN -> {
                    head_img.startNestedScroll(View.SCROLL_AXIS_VERTICAL)
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.e("onCreate", "onCreate: ${event.y.toInt()}");
                    head_img.onNestedPreScroll(head_img,0,event.y.toInt(), IntArray(2))
                }
                MotionEvent.ACTION_UP -> {

                }
            }
            true
        } }



        val list = ArrayList<Int>()
        for (i in 0..19) {
            list.add(i)
        }
        val ad= NestAdapter(this,list)
        recycler_view.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recycler_view.adapter = ad

//        ViewCompat.setNestedScrollingEnabled(recycler_view,false)

    }


}