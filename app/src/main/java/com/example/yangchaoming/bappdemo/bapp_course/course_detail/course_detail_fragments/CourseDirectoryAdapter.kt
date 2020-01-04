package com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseDirectoryItemBean
import kotlinx.android.synthetic.main.item_course_directory.view.*

class CourseDirectoryAdapter(val context: Context, var list: ArrayList<CourseDirectoryItemBean>) : RecyclerView.Adapter<CourseDirectoryAdapter.ViewHolder>() {

    fun resetData(_list: ArrayList<CourseDirectoryItemBean>) {
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_course_directory, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = list[position]
////        holder.image
        holder.name.text = bean.courseName
        holder.title.text = bean.courseTitle
        holder.playDes.text = bean.courseDes
//
        holder.videoTime.text = bean.videoTime

        if(bean.isLock == true){
//            holder.ivPlayLock
        }else{
//            holder.ivPlayLock
        }
//
        holder.rootView.setOnClickListener { listenter?.ItemClick(holder.rootView,position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView
        val image = itemView.iv_img
        val name = itemView.course_name
        val title = itemView.tv_title

        val ivPlayLock = itemView.iv_play_lock
        val playDes = itemView.tv_play_des
        val videoTime = itemView.tv_video_time
    }

    var listenter : CourseItemListener? = null

    interface CourseItemListener{
        fun ItemClick(v:View,pos:Int)
    }
}