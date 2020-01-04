package com.example.yangchaoming.bappdemo.fitness_game.bean

data class FitnessMomentBean(
        var canDel:Boolean?,
        var clockId:String?,
        var commentCount:Int?,
        var content:String?,
        var cpAreaId:String?,
        var cpAreaName:String?,
        var createTime:String?,
        var fileList:ArrayList<FileBean>?,
        var hasFocus: Boolean?,
        var hasLike: Boolean?,
        var headUrl: String?,
        var likeCount: Int?,
        var name: String?,
        var userId: String?,
        var userType: String?

)

//canDel (boolean, optional): 当前动态能否删除 ,
//clockId (string, optional): 动态id ,
//commentCount (integer, optional): 评论数量 ,
//content (string, optional): 动态文字内容 ,
//cpAreaId (string, optional): 所属赛区id ,
//cpAreaName (string, optional): 所属赛区名 ,
//createTime (string, optional): 动态发布时间 yyyy-MM-dd HH:mm ,
//fileList (Array[文件item], optional): 动态的照片/视频绝对地址集合 ,
//hasFocus (boolean, optional): 是否已关注 ,
//hasLike (boolean, optional): 当前用户是否已点过赞 ,
//headUrl (string, optional): 头像图片绝对路径(http://xx..) ,
//likeCount (integer, optional),
//name (string, optional): 姓名 ,
//userId (string, optional): 发动态的bapp教练id/capp会员id ,
//userType (integer, optional): 发动态的用户类型 0:c小程序用户 1:bapp教练 2:capp用户