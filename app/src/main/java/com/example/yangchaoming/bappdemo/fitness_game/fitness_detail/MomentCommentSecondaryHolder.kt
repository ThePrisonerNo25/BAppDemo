package com.example.yangchaoming.bappdemo.fitness_game.fitness_detail

import android.text.SpannableString
import android.text.Spanned
import android.view.View
import com.example.yangchaoming.bappdemo.fitness_game.bean.MomentSecondaryCommentBean
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.item_moment_child_comment.view.*

class MomentCommentSecondaryHolder(itemView: View): ChildViewHolder(itemView){
        val tvCommentReply = itemView.tv_comment_reply

    fun setData(childCommentBean: MomentSecondaryCommentBean){
        if(!childCommentBean.replyToName.isNullOrEmpty() && (childCommentBean.canDel != true)){
           val replyName = "@${childCommentBean.replyToName}:"
            val comment = replyName+childCommentBean.content
            val spannableString =  SpannableString(comment)
            spannableString.setSpan(comment,0,replyName.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            tvCommentReply.text = spannableString
        }else{
            tvCommentReply.text = childCommentBean.content
        }
    }
}