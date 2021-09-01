package com.example.yangchaoming.bappdemo.b_app_work

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_work.*

class WorkActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
        val mockAdapter = MockAdapter(this, 4)
        recycler_view_card.layoutManager = LinearLayoutManager(this)
        recycler_view_card.adapter = mockAdapter

        Handler().postDelayed({
            val layoutParams = recycler_view_card.layoutParams
            layoutParams.width = 0
            layoutParams.height = 0
            recycler_view_card.layoutParams = layoutParams
        },100)
    }
}