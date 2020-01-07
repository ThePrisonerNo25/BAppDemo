package com.example.yangchaoming.bappdemo.community.bean

data class MomentFollowerBean(
        var focusType:Int?,
        var headUrl:String?,
        var name: String?,
        var userId: String?,
        var userType: Int?
)

//focusType (integer, optional): 按钮标识 (0回粉 1相互关注) ,
//headUrl (string, optional): 头像绝对地址 ,
//name (string, optional): 姓名 ,
//userId (string, optional): 用户id ,
//userType (integer, optional): 用户类型 0:c小程序用户 1:bapp独立教练 2:capp用户