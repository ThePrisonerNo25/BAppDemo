package com.example.yangchaoming.bappdemo.action_library.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.item_action_detail_tip.view.*

class ActionDetailAdapter(val context: Context, var list: ArrayList<String>) : RecyclerView.Adapter<ActionDetailAdapter.ViewHolder>() {

    fun resetData(_list:ArrayList<String>){
        list=_list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionDetailAdapter.ViewHolder {
       val view= LayoutInflater.from(context).inflate(R.layout.item_action_detail_tip,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActionDetailAdapter.ViewHolder, position: Int) {
        val bean = list[position]
        holder.tvTip.text=""+position+"、"+ bean
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTip=itemView.tv_tip
    }

}