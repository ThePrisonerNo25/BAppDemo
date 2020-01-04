package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionFilterItemBean
import kotlinx.android.synthetic.main.item_action_filter_inner.view.*

class ActionFilterInnerAdapter(val context: Context, var list: ArrayList<ActionFilterItemBean>, var expand:Boolean) : RecyclerView.Adapter<ActionFilterInnerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionFilterInnerAdapter.ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_action_filter_inner,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       if(list.size>9){
           val _size= if(expand) list.size else 9
           return _size
       }else{
           return list.size
       }
    }

    override fun onBindViewHolder(holder: ActionFilterInnerAdapter.ViewHolder, position: Int) {
        val bean = list[position]
        holder.tvName.text=bean.txt



        when(bean.type){ //选项标识 0：可选 1：不可选 2：当前选中
            0 -> {
                holder.rootView.background=ContextCompat.getDrawable(context,R.drawable.shape_gray_solid_r_2)
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.yj_black))
            }

            1 -> {
                holder.rootView.background=ContextCompat.getDrawable(context,R.drawable.shape_gray_solid_r_2)
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.yj_text_gray))
            }

            2 -> {
                holder.rootView.background=ContextCompat.getDrawable(context,R.drawable.shape_yellow_solid_r_2)
                holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.yj_black))
            }
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView= itemView.rootView
        val tvName= itemView.tv_name
        init {
            rootView.setOnClickListener {  listener?.itemClick(it,adapterPosition) }

        }
    }

    var listener:ActionFilterInnerListener?=null

    interface ActionFilterInnerListener{
        fun itemClick(v:View,pos:Int)
    }

}