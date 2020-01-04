package com.example.yangchaoming.bappdemo.fitness_game.fitness_detail

import android.view.View
import com.example.yangchaoming.bappdemo.fitness_game.bean.MomentCommentBean
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_moment_group_comment.view.*

class MomentCommentHolder(itemView: View) : GroupViewHolder(itemView){
    val avatar =  itemView.avatar
    val tvName = itemView.tv_name
    val tvTimeAddress = itemView.tv_time_address

    fun setData(momentCommentBean: MomentCommentBean) {
//        momentCommentBean
//        avatar
        tvName.text = momentCommentBean.name
        tvTimeAddress.text = "${momentCommentBean.clockId}"

    }

}