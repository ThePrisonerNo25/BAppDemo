package com.example.yangchaoming.bappdemo.demo1.bean

data class DynamicCircleCommentBean (var commentId:String?,
                                     var commentMemberId:String?,
                                     var commentType:Int?,
                                     var content:String?,
                                     var createTime:String?,
                                     var headUrl:String?,
                                     var name:String?,
                                     var replyToName:String?)
//评论信息item {
//    commentId (string, optional): 评论信息id ,
//    commentMemberId (string, optional): 评论人/回复人id ,
//    commentType (integer, optional): 类型 0：普通评论 1：回复 ,
//    content (string, optional): 评论/回复 内容 ,
//    createTime (string, optional): 评论/回复 时间 yyyy-MM-dd HH:mm ,
//    headUrl (string, optional): 评论人头像图片绝对路径(http://xx.. 前端不用拼域名) ,
//    name (string, optional): 评论人姓名 ,
//    replyToName (string, optional): 被回复人姓名
//}