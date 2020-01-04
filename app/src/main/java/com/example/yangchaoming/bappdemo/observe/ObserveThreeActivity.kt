package com.example.yangchaoming.bappdemo.observe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_observer.*
import java.util.*

class ObserveThreeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)

        tv_observe.text="发送massage"

        tv_observe.setOnClickListener {
            val get = AppleObservable.get()
            get.setMsg(AppleBean("苹果","23"))
//            get.notifyObservers(AppleBean("苹果","23"))
//            AppleObservable.get().notifyObservers(OrangeBean("橘子","54"))
        }

        btn_next.setOnClickListener {
            AppleObservable.get().setMsg(OrangeBean("橘子","54"))
        }
    }




}