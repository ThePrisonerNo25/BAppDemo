package com.example.yangchaoming.bappdemo.demo1

import android.content.Context
import android.util.Log
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleBean
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean

class DynamicCirclePresenter(val context: Context,var view:DynamicCircleContract.View ){
    var tag="DynamicCirclePresenter"
    var list:ArrayList<DynamicCircleBean> = ArrayList<DynamicCircleBean>()
    fun  getListData(){
        list.clear()
        for (i in 0..30){

            var c_commentList:ArrayList<DynamicCircleCommentBean> = ArrayList<DynamicCircleCommentBean>();
            var c_likesList:ArrayList<String> = ArrayList<String>()
            var c_photoList:ArrayList<String> = ArrayList<String>()
            val b=i%5+1
            for (j in 1 until b){
                c_likesList.add("likeHeadUrl# $j")
                c_likesList.add("likeHeadUrl# $j")
//                c_likesList.add("likeHeadUrl# $j")
                c_photoList.add("photoUrl# $j")
                var commentObj=DynamicCircleCommentBean("commentId# $i $j","commentMemberId# $j",j%2,"效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！效果很不错！坚持！ $j",
                        "2019-6-4 16:4${j%9}","headUrl $j","评论人姓名 $j","jack $j")
                c_commentList.add(commentObj)
            }
            val p0="clockId#_ $i"
            val p1=i%3
            val p2="type#_ ${i%3}"
            val p3=null
            val p4="第${i}次打卡了，感觉效果特别明显！一个月掉了10斤，坚持坚持坚持呀！"
            val p6="2019-6-4 16:41"
            val p7="headUrl"
            val p8="likeHeadUrlList"
            val p9="memberId#_ $i"
            val p10="memberName#_ $i "
            val p11="photoUrlList "
            val p12="广州市越秀区江湾商业中心"
            val p13="wxUserId#_ $i"
//            Log.e(tag,"c_likesList="+c_likesList.size)
            val dynamicCircleBean = DynamicCircleBean(p0, p1, p2, c_commentList, p4, p6, p7, c_likesList, p9, p10, c_photoList, p11, p12)
            list.add(dynamicCircleBean)
        }

        view.showData(list)

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
    }
}