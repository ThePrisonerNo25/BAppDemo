package com.example.yangchaoming.bappdemo.observe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_observer.*
import java.util.*
import kotlin.collections.ArrayList

class ObserveOneActivity : AppCompatActivity() , Observer{

    var list2: ArrayList<String>? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer)
        AppleObservable.get().addObserver(this)

        btn_next.setOnClickListener {
            startActivity(Intent(this@ObserveOneActivity,ObserveTwoActivity::class.java))
        }


//
        var list1 = arrayListOf<String>("1","2","3","4")
        list2 = list1

        list1.remove("2")

        Log.e("onCreate", "onCreate: $"+ (list1 === list2));
        Log.e("onCreate", "list1: ${System.identityHashCode(list1)}");
        Log.e("onCreate", "list2: ${System.identityHashCode(list2)}");
    }

    override fun onDestroy() {
        AppleObservable.get().deleteObserver(this)
        super.onDestroy()

    }

    override fun update(o: Observable?, arg: Any?) {
        Log.e("update", "update:ObserveOneActivity ");
            if(arg is AppleBean){
                tv_observe.text="物品的名称 #${arg.name } ID是${arg.id}"
            }
    }

}