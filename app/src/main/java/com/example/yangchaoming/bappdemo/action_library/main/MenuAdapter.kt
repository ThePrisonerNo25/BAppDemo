package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.MenuBean
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(var list: ArrayList<MenuBean>, val context: Context) : RecyclerView.Adapter<MenuAdapter.Holder>() {

    fun resetData(_list: ArrayList<MenuBean>){
        list = _list
        notifyDataSetChanged()
    }

    fun addData(_list: ArrayList<MenuBean>){
        list.addAll(_list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menuBean = list[position]
        holder.rootView.isSelected=menuBean.selector
        holder.tvMenu.text=menuBean.name+"(${menuBean.childId?.size})"

        holder.rootView.setOnClickListener { menuList?.itemClick(holder.rootView,position) }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView
        val tvMenu=itemView.tv_menu

    }

    interface MenuListener{
        fun itemClick(v:View,pos:Int)
    }

    var menuList: MenuListener?=null

}