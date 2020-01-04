package com.example.yangchaoming.bappdemo.transiton

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.item_one.view.*

class OneAdapter (val context: Context,var list:List<String> ):RecyclerView.Adapter<OneAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item_one, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivImg.transitionName=""+holder.ivImg.id+position
        holder.ivImg.setOnClickListener {  listener?.ItemClick(it,position)}

        if(position%2 ==1){
            holder.ivImg.setImageResource(R.mipmap.ic_continue)
        }else{
            holder.ivImg.setImageResource(R.mipmap.ic_launcher)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val ivImg= item.img

    }

    interface OneAdapterListener{
        fun ItemClick(v:View,pos:Int)
    }
    var listener:OneAdapterListener? =null
}