package com.example.yangchaoming.bappdemo.video

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.video.bean.VideoInfoBean
import com.example.yangchaoming.bappdemo.video.videoTrim.VideoUtil
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter (val context: Context,var list:ArrayList<VideoInfoBean>):RecyclerView.Adapter<VideoAdapter.ViewHolder>(){

    fun resetData(list_:ArrayList<VideoInfoBean>){
        list = list_
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val formNameView = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(formNameView)
    }

    override fun getItemCount(): Int {
      return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoInfoBean = list[position]
        val videoUri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoInfoBean.id)
        Glide.with(context)
                .load(videoUri)
                .into(holder.photo)

//        holder.ivCheck.visibility = if(videoInfoBean.seletect==true) View.VISIBLE else View.GONE
        holder.ivPhotoFront.visibility = if(videoInfoBean.seletect==true) View.VISIBLE else View.GONE


        holder.ivCheck.setOnClickListener {
            listListener?.checkItem(it,position)
        }
        holder.photo.setOnClickListener {
            listListener?.clickItem(it,position)
        }

        holder.tvDuration.text = VideoUtil.convertSecondsToTime(videoInfoBean.duration)

    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val photo = itemView.photo
        val ivCheck = itemView.iv_check
        val ivPhotoFront = itemView.iv_photo_front
        val tvDuration = itemView.tv_duration
    }

    var listListener:VideoListListener? = null

    interface VideoListListener{
        fun checkItem(v:View,pos:Int)
        fun clickItem(v:View,pos:Int)
    }

}