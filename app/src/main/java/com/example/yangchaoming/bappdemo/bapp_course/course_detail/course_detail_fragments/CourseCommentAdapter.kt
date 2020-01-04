package com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseCommentBean
import kotlinx.android.synthetic.main.item_course_comment.view.*
import kotlinx.android.synthetic.main.item_course_directory.view.*

class CourseCommentAdapter(val context: Context, var list: ArrayList<CourseCommentBean>) : RecyclerView.Adapter<CourseCommentAdapter.ViewHolder>() {

    fun resetData(_list: ArrayList<CourseCommentBean>) {
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_course_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = list[position]
////        holder.image
        holder.name.text = bean.name
        holder.time.text = bean.createTime
        holder.comment.text = bean.comment
//
        holder.rootView.setOnClickListener { listenter?.ItemClick(holder.rootView,position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView
        val avatar = itemView.avatar
        val name = itemView.tv_name
        val time = itemView.tv_time
        val comment = itemView.tv_comment
    }

    var listenter : CourseItemListener? = null

    interface CourseItemListener{
        fun ItemClick(v:View,pos:Int)
    }
}