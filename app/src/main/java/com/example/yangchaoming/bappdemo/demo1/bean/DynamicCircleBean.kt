package com.example.yangchaoming.bappdemo.demo1.bean

data class DynamicCircleBean (var clockId:String?,
                              var clockType:Int?,
                              var clockTypeStr:String?,
                              var commentList:ArrayList<DynamicCircleCommentBean>?,
                              var content:String?,
                              var createTime:String?,
                              var headUrl:String?,
                              var likeHeadUrlList:ArrayList<String>?,
                              var memberId:String?,
                              var name:String?,
                              var photoUrlList:ArrayList<String>?,
                              var site:String?,
                              var wxUserId:String? )

//会员动态item {
//    clockId (string, optional): 动态信息id ,
//    clockType (integer, optional): 打卡类型 ,
//    clockTypeStr (string, optional): 打卡类型文字 ,
//    commentList (Array[评论信息item], optional): 评论信息集合 ,
//    content (string, optional): 动态内容 ,
//    createTime (string, optional): 动态发布时间 yyyy-MM-dd HH:mm ,
//    headUrl (string, optional): 会员头像图片绝对路径(http://xx.. 前端不用拼域名) ,
//    likeHeadUrlList (Array[string], optional): 点赞头像图片绝对地址集合(http://xx..前端不用拼域名) ,
//    memberId (string, optional): bapp会员id (跳会员详情用这个) ,
//    name (string, optional): 会员姓名 ,
//    photoUrlList (Array[string], optional): 动态照片_绝对地址集合(http://xx..前端不用拼域名) ,
//    site (string, optional): 发布评论时的地点名称 ,
//    wxUserId (string, optional): 小程序用户id (跳粉丝详情用这个)
//}
