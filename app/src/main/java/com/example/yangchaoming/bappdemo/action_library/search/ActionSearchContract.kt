package com.example.yangchaoming.bappdemo.action_library.search

import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean

interface ActionSearchContract {
    interface View{
        fun showActionList(actionList: ArrayList<ActionItemBean>)

    }
}