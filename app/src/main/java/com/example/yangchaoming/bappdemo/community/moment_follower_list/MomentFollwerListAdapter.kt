package com.example.yangchaoming.bappdemo.community.moment_follower_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.bean.MomentFollowerBean
import kotlinx.android.synthetic.main.item_moment_follower.view.*

class MomentFollwerListAdapter (val context: Context, var list :ArrayList<MomentFollowerBean> ): RecyclerView.Adapter<MomentFollwerListAdapter.Holder>(){
    fun resetData( _list :ArrayList<MomentFollowerBean> ){
        list = _list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_moment_follower,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    inner class Holder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.avatar
        val tv_name = itemView.tv_name
        val tv_moment_num = itemView.tv_moment_num
        val tv_fans_num = itemView.tv_fans_num
        val btn_attention = itemView.btn_attention
    }

}