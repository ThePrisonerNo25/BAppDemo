package com.example.yangchaoming.bappdemo.demo1.message_list

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicUtil
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleMessageBean
import kotlinx.android.synthetic.main.item_dynamic_circle_message.view.*

class DynamicCircleMessagesAdapter(var context:Context,var list:List<DynamicCircleMessageBean>) : RecyclerView.Adapter<DynamicCircleMessagesAdapter.Holder>(){

    fun resetData(list_: List<DynamicCircleMessageBean>){
        list = list_
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DynamicCircleMessagesAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_circle_message, p0, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(p0: DynamicCircleMessagesAdapter.Holder, p1: Int) {
        val bean = list[p1]
//     avatar   bean.headUrl
        p0.tvName.text= bean.name ?:" "
        p0.tvTime.text=DynamicUtil.getFormatDay(bean.createTime ?: "")
        when(bean.msgType){//0 点赞 1 评论
            0 -> {
                p0.ivGood.visibility=View.VISIBLE
                p0.tvReplyContent.visibility=View.GONE
            }

            1 -> {
                p0.tvReplyContent.text=bean.comment ?: " "
                p0.ivGood.visibility=View.GONE
                p0.tvReplyContent.visibility=View.VISIBLE
            }
        }

            p0.ivComment.visibility= if(TextUtils.isEmpty(bean.photoUrl)) View.GONE else View.VISIBLE
            p0.tvComment.visibility=if(TextUtils.isEmpty(bean.photoUrl)) View.VISIBLE else View.GONE
            p0.tvComment.text=if(TextUtils.isEmpty(bean.photoUrl)) bean.content else ""
    }


    inner class Holder(itemView:View) : RecyclerView.ViewHolder(itemView){
       var avatar = itemView.avatar!!
       var tvName = itemView.tv_name!!
       var ivGood = itemView.iv_good!!
       var tvReplyContent = itemView.tv_reply_content!!
       var tvTime = itemView.tv_time!!
       var tvComment = itemView.tv_comment!!
       var ivComment = itemView.iv_comment!!
    }

}