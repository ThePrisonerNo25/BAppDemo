package com.example.yangchaoming.bappdemo.demo1

import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleBean

interface DynamicCircleContract{
    interface View{
       fun showData(list: ArrayList<DynamicCircleBean>)
    }
}