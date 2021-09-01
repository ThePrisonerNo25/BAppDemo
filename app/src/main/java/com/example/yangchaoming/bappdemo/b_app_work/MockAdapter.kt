package com.example.yangchaoming.bappdemo.b_app_work

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R

class MockAdapter (val context: Context,var num:Int):RecyclerView.Adapter<MockAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mock,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return num
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv.setOnClickListener {
            Toast.makeText(context,"text pos is #$position",Toast.LENGTH_SHORT).show()
        }
    }

    inner class Holder(val view: View):RecyclerView.ViewHolder(view) {
       val tv = view.findViewById<TextView>(R.id.tv)
    }

}