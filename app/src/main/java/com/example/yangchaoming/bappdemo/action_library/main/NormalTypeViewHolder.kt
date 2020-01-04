package com.example.yangchaoming.bappdemo.action_library.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_detail_action_normal.view.*

class NormalTypeViewHolder (itemView: View ) :RecyclerView.ViewHolder(itemView){
    val ivCheck= itemView.iv_check
    val tvActionName =itemView.tv_action_name
    val tvActionUnit =itemView.tv_action_unit
    val swipeLayout=itemView.swipe_layout
    val btnDelete=itemView.btn_delete
    init {

    }
}