package com.example.yangchaoming.bappdemo.timetable

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.activity_test.view.*
import kotlinx.android.synthetic.main.item_day_table.view.*
import kotlinx.android.synthetic.main.item_time_table.view.*

class TimeTableAdapter (val context: Context,var list:ArrayList<CourseInfoBean>,var month:String):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val clockList = arrayListOf<String>("06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00",
            "14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00",
            "22:00","23:00"
    )
    companion object{
        const val TYPE_TIME = 0
        const val TYPE_DAY =1
    }


    fun resetData(_list:ArrayList<CourseInfoBean>,_month:String){
        list = _list
        month = _month
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_TIME){
            val view = LayoutInflater.from(context).inflate(R.layout.item_time_table,parent,false)
            return TimeHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.item_day_table,parent,false)
            return DayHolder(view)
        }
    }

    override fun getItemCount(): Int  = 8

    override fun getItemViewType(position: Int): Int {
        return if(position==0)  TYPE_TIME else TYPE_DAY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TimeHolder){
            holder.ll_container_time.removeAllViews()
            val textView =  TextView(context)
            val layoutParams = textView.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            layoutParams.height =  100
            textView.gravity =  Gravity.CENTER_HORIZONTAL
            textView.text = month
            holder.ll_container_time.addView(textView)

            for (i in 0 until clockList.size){
                val textView =  TextView(context)
                val layoutParams = textView.layoutParams as LinearLayout.LayoutParams
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                layoutParams.height =  100
                textView.gravity =  Gravity.CENTER_HORIZONTAL
                textView.text = clockList[i]
                holder.ll_container_time.addView(textView)
            }

        }else if(holder is DayHolder){

        }
    }


    class TimeHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
//            val recyclerView  = itemView.recycler_time
//            val front_container  =  itemView.front_container
        val ll_container_time =  itemView.ll_container_time
    }

    class DayHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
//            val recycler_day = itemView.recycler_day
//            val front_container_day = itemView.front_container_day
        val ll_container_day = itemView.ll_container_day
    }

}