package com.example.yangchaoming.bappdemo.miscellaneous

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R

class TestActivity4 :AppCompatActivity(){
    val tag = "TestActivity4";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test3)

        var sourceList=ArrayList<PersonBean>()
       var a1= PersonBean("name1", "1")
       var a2= PersonBean("name2", "1")
       var a3= PersonBean("name3", "1")

        sourceList.add(a1)
        sourceList.add(a2)
        sourceList.add(a3)


        var comList=ArrayList<PersonBean>()

        var a0= PersonBean("name0", "2")
        var a4= PersonBean("name4", "3")
        comList.add(a0)
        comList.addAll(sourceList)
        comList.add(a4)

        Log.e(tag, "onCreate: comList="+comList.toString());

        sourceList.remove(a2)

        Log.e(tag, "onCreate: comList2222="+comList.toString());
    }
}