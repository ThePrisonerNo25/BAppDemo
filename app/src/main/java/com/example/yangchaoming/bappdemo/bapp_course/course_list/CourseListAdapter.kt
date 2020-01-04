package com.example.yangchaoming.bappdemo.bapp_course.course_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean
import kotlinx.android.synthetic.main.item_course_list.view.*

class CourseListAdapter(val context: Context, var list: ArrayList<FitnessCourseBean>) : RecyclerView.Adapter<CourseListAdapter.ViewHolder>() {

    fun resetData(_list: ArrayList<FitnessCourseBean>) {
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = list[position]
//        holder.image
        holder.name.text = bean.name
        holder.title.text = bean.title
        holder.courseNumAndViewNum.text = "${bean.videoNum}个视频 / ${bean.watchNum}次观看"
        if(bean.isCharge == 0){ //0不收费,1收费 ,
            holder.price.text = "免费"
        }else{
            holder.price.text = bean.sellingPrice
        }

        holder.rootView.setOnClickListener { listenter?.ItemClick(holder.rootView,position) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView = itemView
        val image = itemView.iv_img
        val name = itemView.course_name
        val title = itemView.tv_title
        val courseNumAndViewNum = itemView.course_num_and_view_num
        val price = itemView.tv_price
    }

    var listenter : CourseItemListener? = null

    interface CourseItemListener{
        fun ItemClick(v:View,pos:Int)
    }
}