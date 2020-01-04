package com.example.yangchaoming.bappdemo.action_library.detail

import android.content.Context
import com.example.yangchaoming.bappdemo.action_library.bean.ActionDetailBean

class ActionDetailPresenter (val context: Context,val view:ActionDetailContract.View){
    fun getDetailInfo(){
        mockDetailInfo()
        view.showDetail( mockDetailInfo())
    }

    fun mockDetailInfo():ActionDetailBean{
//        动作详情 {
//            actLevelStr (string, optional): 难度 ,
//            actTypeStr (string, optional): 类型 ,
//            actionTips (Array[string], optional): 动作要领文字集合(数组换行) ,
//            equipmentSortStr (string, optional): 器械要求 ,
//            id (integer, optional),
//            muscleImgList (Array[string], optional): 主要肌肉示意图集合 (绝对地址) ,
//            name (string, optional): 动作名称 ,
//            otherMuscles (string, optional): 其他肌肉群 ,
//            trainPartName (string, optional): 主要肌肉群
//        }

        return ActionDetailBean("简单","徒手", arrayListOf("1、挺胸收腹，躯干与地面平行","2、双手与肩同宽，始终保持腰背挺直，控制肘部紧贴身体两侧。"),
                "徒手",10, arrayListOf("urlimg1","urlimg2"),"四足俯卧撑","肱三头肌","胸大肌")
    }
}