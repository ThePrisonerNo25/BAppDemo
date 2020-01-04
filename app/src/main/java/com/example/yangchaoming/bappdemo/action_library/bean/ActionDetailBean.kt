package com.example.yangchaoming.bappdemo.action_library.bean

data class ActionDetailBean (
        val actLevelStr:String,
        val actTypeStr:String,
        val actionTips:ArrayList<String>,
        val equipmentSortStr:String,
        val id:Int,
        val muscleImgList:ArrayList<String>,
        val name:String,
        val otherMuscles:String,
        val trainPartName:String
        )

//动作详情 {
//    actLevelStr (string, optional): 难度 ,
//    actTypeStr (string, optional): 类型 ,
//    actionTips (Array[string], optional): 动作要领文字集合(数组换行) ,
//    equipmentSortStr (string, optional): 器械要求 ,
//    id (integer, optional),
//    muscleImgList (Array[string], optional): 主要肌肉示意图集合 (绝对地址) ,
//    name (string, optional): 动作名称 ,
//    otherMuscles (string, optional): 其他肌肉群 ,
//    trainPartName (string, optional): 主要肌肉群
//}