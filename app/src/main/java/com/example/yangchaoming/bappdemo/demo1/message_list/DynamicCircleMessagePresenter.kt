package com.example.yangchaoming.bappdemo.demo1.message_list

import android.content.Context
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleMessageBean

class DynamicCircleMessagePresenter(var context: Context,var view:DynamicCircleMessageContract.View){
    fun getMessageList(){
        var list=ArrayList<DynamicCircleMessageBean>()
        for ( i in 0 until 10){
            val bean = DynamicCircleMessageBean("clockid #$i", "comment#$i", "content $i", "2019-06-14 11:08", null, i % 2, "name", null)
            list.add(bean)
        }
        view.showDataList(list)

    }
}