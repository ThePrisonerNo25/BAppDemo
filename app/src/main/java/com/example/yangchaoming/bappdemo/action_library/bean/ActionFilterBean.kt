package com.example.yangchaoming.bappdemo.action_library.bean

data class ActionFilterBean(var actLevelList:ArrayList<ActionFilterItemBean>,var actTypeList:ArrayList<ActionFilterItemBean>
                       ,var equipmentFixedList:ArrayList<ActionFilterItemBean>
                       ,var equipmentFreedomList:ArrayList<ActionFilterItemBean>
                       ,var equipmentOneList:ArrayList<ActionFilterItemBean>)
//动作属性筛选项 {
////    actLevelList (Array[动作属性选项item], optional): 难度 ,
////    actTypeList (Array[动作属性选项item], optional): 类型 ,
////    equipmentFixedList (Array[动作属性选项item], optional): 固定器械 ,
////    equipmentFreedomList (Array[动作属性选项item], optional): 自由器械 ,
////    equipmentOneList (Array[动作属性选项item], optional): 器械要求
////}
////动作属性选项item {
////    id (string, optional): 选项id ,
////    txt (string, optional): 选项名 ,
////    type (integer, optional): 选项标识 0：可选 1：不可选 2：当前选中
////}