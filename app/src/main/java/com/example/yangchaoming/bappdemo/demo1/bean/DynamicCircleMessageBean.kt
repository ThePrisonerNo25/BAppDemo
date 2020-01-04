package com.example.yangchaoming.bappdemo.demo1.bean

data class DynamicCircleMessageBean(var clockId: String?,
                                    var comment: String?,
                                    var content: String?,
                                    var createTime: String?,
                                    var headUrl: String?,
                                    var msgType: Int?,
                                    var name: String?,
                                    var photoUrl: String?
)
//会员动态未读消息item {
//clockId (string, optional): 动态id ,
//comment (string, optional): 评论内容 ,
//content (string, optional): 动态文字内容 ,
//createTime (string, optional): 消息创建时间(yyyy-MM-dd HH:mm) ,
//headUrl (string, optional): 产生消息人的头像绝对地址 ,
//msgType (integer, optional): 未读消息类型(0 点赞 1 评论) ,
//name (string, optional): 产生消息人的姓名 ,
//photoUrl (string, optional): 动态其中一张图的绝对地址(如果没有图片则 前端取content放图片位置显示)
//}