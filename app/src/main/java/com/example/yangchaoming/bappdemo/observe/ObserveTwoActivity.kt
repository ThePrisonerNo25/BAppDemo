package com.example.yangchaoming.bappdemo.observe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_observer.*
import java.util.*

class ObserveTwoActivity : AppCompatActivity() , Observer{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)
        AppleObservable.get().addObserver(this)

        btn_next.setOnClickListener {
            startActivity(Intent(this@ObserveTwoActivity,ObserveThreeActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        AppleObservable.get().deleteObserver(this)
        super.onDestroy()

    }

    override fun update(o: Observable?, arg: Any?) {
        Log.e("update", "update:ObserveTwoActivity ");
        if(arg is OrangeBean){
            tv_observe.text="物品的名称 #${arg.name } ID是${arg.id}"
        }
    }

}