package com.example.yangchaoming.bappdemo.demo1.message_list

import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleMessageBean

interface DynamicCircleMessageContract{
   interface View{
       fun showDataList(list: List<DynamicCircleMessageBean>)
   }
}