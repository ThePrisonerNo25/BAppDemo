package com.example.yangchaoming.bappdemo.b_app_demo.viewpage2

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.fragment_collection_object.view.*

class DemoCollectionAdapter2(val context:Context ,var list:ArrayList<Int>): RecyclerView.Adapter<DemoCollectionAdapter2.Holder>() {

    fun resetList(list_:ArrayList<Int>){
        list = list_
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoCollectionAdapter2.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_collection_object,parent,false)
        val tv = view.findViewById<TextView>(R.id.text1)
        val layoutParams = tv.layoutParams
        layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300f,context.resources.displayMetrics).toInt()
        tv.layoutParams =layoutParams
        return Holder(view)
    }

    override fun onBindViewHolder(holder: DemoCollectionAdapter2.Holder, position: Int) {
        val i = list[position]
        holder.name.text = i.toString()
        when(i){
            0 -> {holder.name.setBackgroundResource(R.color.colorAccent)}
            1 -> {holder.name.setBackgroundResource(R.color.colorPrimary)}
            2 -> {holder.name.setBackgroundResource(R.color.colorPrimaryDark)}
            3 -> {holder.name.setBackgroundResource(R.color.color_selectbtn_select)}
            4 -> {holder.name.setBackgroundResource(R.color.color_main2)}
            5 -> {holder.name.setBackgroundResource(R.color.colorgray)}
            else -> {holder.name.setBackgroundResource(R.color.colorAccent)}
        }
    }

    public fun getItem(pos:Int):Int?{
        if(pos<list.size){
            return list[pos]
        }else{
            return null
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(view: View):RecyclerView.ViewHolder(view) {
        val name = view.text1
        val itemview_ = view
    }
}