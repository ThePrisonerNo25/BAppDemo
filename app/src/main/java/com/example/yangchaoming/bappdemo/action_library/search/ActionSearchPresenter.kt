package com.example.yangchaoming.bappdemo.action_library.search

import android.content.Context
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean

class ActionSearchPresenter (val context:Context ,val view: ActionSearchContract.View){
    var actionList: ArrayList<ActionItemBean> = ArrayList()

    var selectedIds:ArrayList<Int> = ArrayList()

    fun getActionList(keyWord:String){
        mockData()
        mapSeletedItem()
        view.showActionList(actionList)
    }

    fun  mockData(){
        repeat(5){
//            actionList.add(ActionItemBean("actionName #_$it","$it x ${it%5}", it,false))
            actionList.add(ActionItemBean("actGroup","3","50",it%3,it%2,it.toString(),1,"name $it",it,false))
        }
    }

    fun mapSeletedItem(){
        actionList.forEach { if(selectedIds.contains(it.id.toInt())) it.selected=true }
    }

}
