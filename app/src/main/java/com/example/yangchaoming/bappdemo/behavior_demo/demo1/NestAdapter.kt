package com.example.yangchaoming.bappdemo.behavior_demo.demo1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R

class NestAdapter (val context: Context,var list:ArrayList<Int> ):RecyclerView.Adapter<NestAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_nest, parent, false)
        return Holder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val i = list[position]
        holder.text.setText("#num $i")
        if(i%2==0){
            holder.itemView_.setBackgroundResource(R.color.light_green)
        }else{
            holder.itemView_.setBackgroundResource(R.color.yj_light_yellow)
        }
    }

    class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemView_ = itemView
        val text= itemView.findViewById<TextView>(R.id.tv_content)
    }

}