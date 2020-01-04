package com.example.yangchaoming.bappdemo.fitness_game.fitness_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.fitness_game.bean.MomentCommentBean
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class FitnessMomentDetailAdapter(val context: Context,var list: ArrayList<MomentCommentBean>)
    : ExpandableRecyclerViewAdapter<MomentCommentHolder,MomentCommentSecondaryHolder>(list){

    fun resetData(_list: ArrayList<MomentCommentBean>){
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): MomentCommentHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_moment_group_comment,parent,false)
        return MomentCommentHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): MomentCommentSecondaryHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_moment_child_comment,parent,false)
        return MomentCommentSecondaryHolder(view)
    }

    override fun onBindChildViewHolder(holder: MomentCommentSecondaryHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {
        if((group as MomentCommentBean).replyList.isNullOrEmpty()) return
        val childCommentBean=  (group as MomentCommentBean).replyList!![childIndex]
        holder?.setData(childCommentBean)
//        holder?.itemView.setOnClickListener()
    }

    override fun onBindGroupViewHolder(holder: MomentCommentHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        if(group == null)return
        holder?.setData((group as MomentCommentBean))

    }

}