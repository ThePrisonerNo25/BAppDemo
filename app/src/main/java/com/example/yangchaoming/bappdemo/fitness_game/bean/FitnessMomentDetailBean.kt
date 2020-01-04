package com.example.yangchaoming.bappdemo.fitness_game.bean

data class FitnessMomentDetailBean(
        var address: String?,
        var canDel: Boolean?,
        var clockId: String?,
        var clockType: Int?,
        var clockTypeStr: String?,
        var commentCount: Int?,
        var commentPageVO: ArrayList<MomentCommentWrapBean>?,
        var content: String?,
        var cpArea: String?,
        var createTime: String?,
        var fileList: ArrayList<FileBean>?,
        var hasFocus: Boolean?,
        var hasLike: Boolean?,
        var headUrl: String?,
        var likeCount: Int?,
        var name: String?,
        var userId: String?,
        var userType: Int?

)

//address (string, optional): 动态发布的地址 ,
//canDel (boolean, optional): 当前动态能否删除 ,
//clockId (string, optional): 动态id ,
//clockType (integer, optional): 打卡类型标签 0 训练打卡 1饮食打卡 2普通动态 ,
//clockTypeStr (string, optional): 打卡类型标签文字 ,
//commentCount (integer, optional): 评论数量 ,
//commentPageVO (PageVO分页数据视图对象«赛事动态评论item», optional): 1级评论分页信息 ,
//content (string, optional): 动态文字内容 ,
//cpArea (string, optional): 赛区标签 ,
//createTime (string, optional): 动态发布时间 yyyy-MM-dd HH:mm ,
//fileList (Array[文件item], optional): 动态的照片/视频绝对地址集合 ,
//hasFocus (boolean, optional): 是否已关注 ,
//hasLike (boolean, optional): 当前用户是否已点过赞 ,
//headUrl (string, optional): 头像图片绝对路径(http://xx..) ,
//likeCount (integer, optional),
//name (string, optional): 姓名 ,
//userId (string, optional): 发动态的作者id ,
//userType (integer, optional): 发动态的用户类型 0:c小程序用户 1:bapp教练 2:capp用户