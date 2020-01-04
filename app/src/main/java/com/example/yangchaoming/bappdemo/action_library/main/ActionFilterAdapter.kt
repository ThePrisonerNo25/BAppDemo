package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionFilterItemBean
import com.example.yangchaoming.bappdemo.action_library.bean.ActionWrapFilterBean
import kotlinx.android.synthetic.main.item_action_filter.view.*

class ActionFilterAdapter(val context: Context,var list:ArrayList<ActionWrapFilterBean>) : RecyclerView.Adapter<ActionFilterAdapter.Holder>(){

    fun resetData(_list: ArrayList<ActionWrapFilterBean>){
        list=_list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionFilterAdapter.Holder {
       var view= LayoutInflater.from(context).inflate(R.layout.item_action_filter,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: ActionFilterAdapter.Holder, position: Int) {
        val bean = list[position]

        holder.tvTitle.text=bean.title
        holder.ivDownArrow.visibility=if(!bean.expand&&bean.list.size>9) View.VISIBLE else View.INVISIBLE

        holder.innerRecyclerView.layoutManager=GridLayoutManager(context,3)
        val innerAdapter=ActionFilterInnerAdapter(context,bean.list,bean.expand)
        holder.innerRecyclerView.setHasFixedSize(true)
        holder.innerRecyclerView.adapter=innerAdapter

//        holder.innerRecyclerView.removeItemDecoration()
        val itemDecorationCount = holder.innerRecyclerView.itemDecorationCount
        if(itemDecorationCount==0){
            val space : Int=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,context.resources.displayMetrics).toInt()
            holder.innerRecyclerView.addItemDecoration( GridItemDecoration(space,3))
        }


        innerAdapter.listener=object :ActionFilterInnerAdapter.ActionFilterInnerListener{
            override fun itemClick(v: View, pos: Int) {
                listener?.itemClick(v,holder.rootView,pos,position)
            }
        }

        holder.ivDownArrow.setOnClickListener {  listener?.arrowClick(it,position) }

    }

   inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView) {
       val rootView=itemView.rootView
        val tvTitle=itemView.tv_title
        val innerRecyclerView =itemView.inner_recycler_view
        val ivDownArrow=itemView.iv_down_arrow
    }

    var listener:ActionFilterListener?=null

    interface ActionFilterListener{
        fun itemClick(childView:View,parentView:View,childPos:Int,parentPos:Int)
        fun arrowClick(view:View,parentPos:Int)
    }

}