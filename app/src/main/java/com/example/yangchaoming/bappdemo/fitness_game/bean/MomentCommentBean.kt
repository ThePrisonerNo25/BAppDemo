package com.example.yangchaoming.bappdemo.fitness_game.bean

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class MomentCommentBean(
        var canDel: Boolean?,
        var clockId: String?,
        var commentId: String?,
        var content: String?,
        var createTime: String?,
        var headUrl: String?,
        var name: String?,
        var replyList: ArrayList<MomentSecondaryCommentBean>?,
        var userId: String?
) : ExpandableGroup<MomentSecondaryCommentBean>(commentId,replyList)

//canDel (boolean, optional): 当前评论item能否删除 ,
//clockId (string, optional): 被评论的动态id ,
//commentId (string, optional): 评论id ,
//content (string, optional): 评论/回复 内容 ,
//createTime (string, optional): 评论 时间 yyyy-MM-dd HH:mm ,
//headUrl (string, optional): 评论人头像图片绝对路径 ,
//name (string, optional): 评论人姓名 ,
//replyList (Array[赛事动态2级评论/回复列表item], optional): 2级评论/回复列表 ,
//userId (string, optional): 评论人/回复人id (教练或capp用户id)