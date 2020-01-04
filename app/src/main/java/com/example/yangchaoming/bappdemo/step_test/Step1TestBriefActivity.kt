package com.example.yangchaoming.bappdemo.step_test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R

class Step1TestBriefActivity : AppCompatActivity() {
    val tag = "Step1TestBriefActivity";
    private lateinit var btnInput: Button
    private lateinit var btnReady:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step1_test_brief)
        initView()
    }

    private fun initView() {
        btnInput=findViewById(R.id.btn_input)
        btnReady=findViewById(R.id.btn_ready)
        btnInput.setOnClickListener { v->run{
            val intent = Intent(this, Step1TestActivity::class.java)
            this.startActivity(intent)
        } }

        btnReady.setOnClickListener {  }


    }
}
