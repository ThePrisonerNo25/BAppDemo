package com.example.yangchaoming.bappdemo.action_library.bean

data class MenuBean (var name:String,val id:Int,var libType:Int,var childId:ArrayList<Int>):BaseSelectorBean()

//通用数据列表容器对象«训练部位列表item» {
////    dataList (Array[训练部位列表item], optional): 数据列表
////}
////训练部位列表item {
////    id (integer, optional): 训练部位id ,
////    libType (integer, optional): 动作库数据类型(0：系统预置 1：自定义) ,
////    name (string, optional): 部位名
////}