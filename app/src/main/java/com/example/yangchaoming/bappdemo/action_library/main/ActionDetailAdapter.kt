package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import kotlin.collections.ArrayList
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.yangchaoming.bappdemo.R


class ActionDetailAdapter(var list :ArrayList<ActionItemBean>, val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var viewType: Int = CUSTOM_TYPE
    private val viewBinderHelper = ViewBinderHelper()
    companion object {
        val CUSTOM_TYPE = 0
        val NORMAL_TYPE = 1
    }


    fun resetData(_list :ArrayList<ActionItemBean>){
        list=_list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        when(viewType == CUSTOM_TYPE){
            true -> return CustomTypeViewHolder(inflater.inflate(R.layout.item_detail_action_custom, parent, false))

            false -> return NormalTypeViewHolder(inflater.inflate(R.layout.item_detail_action_normal, parent, false))
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = list[position]
        if(holder is CustomTypeViewHolder){
            when(bean.selected){
                true -> holder.ivCheck.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background))
                false -> holder.ivCheck.setImageDrawable(context.getDrawable(R.drawable.shape_circle_yellow))
            }

            holder.tvActionName.text=bean.name

            var unit=if(bean.actType==0) "次" else "秒"
            holder.tvActionUnit.text=""+bean.actNum+unit+"x"+bean.actGroupNum

            holder.ivClose.setOnClickListener {
                itemListener?.delete(it,position)
            }

            holder.ivCheck.setOnClickListener { itemListener?.selected(it,position) }

        }else if(holder is NormalTypeViewHolder){
            when(bean.selected){
                true -> holder.ivCheck.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background))
                false -> holder.ivCheck.setImageDrawable(context.getDrawable(R.drawable.shape_circle_yellow))
            }
            holder.tvActionName.text=bean.name

            var unit=if(bean.actType==0) "次" else "秒"
            holder.tvActionUnit.text=""+bean.actNum+unit+"x"+bean.actGroupNum


            holder.ivCheck.setOnClickListener { itemListener?.selected(it,position) }

            viewBinderHelper.bind(holder.swipeLayout,bean.id)
            holder.btnDelete.setOnClickListener { Toast.makeText(context,"name="+bean.name,Toast.LENGTH_SHORT).show() }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    var itemListener: ActionDetailListener?=null

    interface ActionDetailListener{
        fun selected(v: View,pos:Int)
        fun delete(v: View,pos:Int)
    }

}