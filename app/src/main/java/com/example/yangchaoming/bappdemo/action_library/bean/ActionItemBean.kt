package com.example.yangchaoming.bappdemo.action_library.bean

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ActionItemBean (val actGroup:String, val actGroupNum:String,var actNum: String, var actType:Int,
                           var actUnit:Int,val id:String, val libType:Int,val name:String,val trainPartId:Int,
                           var selected:Boolean=false):Parcelable

//actGroup (string, optional): 动作组完整文字 ,
//actGroupNum (string, optional): 动作组_组数 ,
//actNum (string, optional): 动作组_次数/秒数 ,
//actType (integer, optional): 动作类型 0热身 1训练 2拉伸 ,
//actUnit (integer, optional): 动作组_单位： 0：次 1：秒 ,
//id (string, optional): 动作id ,
//libType (integer, optional): 动作库数据类型(0：系统预置 1：自定义 ,
//name (string, optional): 动作名称 ,
//trainPartId (integer, optional): 训练部位id