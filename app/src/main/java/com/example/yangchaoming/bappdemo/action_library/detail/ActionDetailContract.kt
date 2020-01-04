package com.example.yangchaoming.bappdemo.action_library.detail

import com.example.yangchaoming.bappdemo.action_library.bean.ActionDetailBean

interface ActionDetailContract {
    interface View{
        fun showDetail(mockDetailInfo: ActionDetailBean)
    }
}