package com.example.yangchaoming.bappdemo.fitness_game.bean

data class MomentCommentWrapBean(
        var pageNum: Int?,
        var pageSize: Int?,
        var pages: Int?,
        var total: Int?,
        var records: ArrayList<MomentCommentBean>?
)

//pageNum (integer, optional): 当前页码 ,
//pageSize (integer, optional): 每页显示记录数 ,
//pages (integer, optional): 总页数 ,
//records (Array[赛事动态评论item], optional): 查询结果数据集 ,
//total (integer, optional): 总记录数