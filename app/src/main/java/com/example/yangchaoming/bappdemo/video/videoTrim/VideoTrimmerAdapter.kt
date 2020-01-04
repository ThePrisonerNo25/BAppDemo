package com.example.yangchaoming.bappdemo.video.videoTrim

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

import com.example.yangchaoming.bappdemo.R

import java.util.ArrayList

/**
 * author : J.Chou
 * e-mail : who_know_me@163.com
 * time   : 2018/05/30/4:20 PM
 * version: 1.0
 * description:
 */
class VideoTrimmerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mBitmaps:ArrayList<Bitmap> = ArrayList()
    private val mInflater: LayoutInflater
    init {
        this.mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrimmerViewHolder(mInflater.inflate(R.layout.video_thumb_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = mBitmaps[position]
        (holder as TrimmerViewHolder).thumbImageView.setImageBitmap(bean)
    }

    override fun getItemCount(): Int {
        return mBitmaps.size
    }

    fun addBitmaps(bitmap: Bitmap) {
        mBitmaps.add(bitmap)
        notifyDataSetChanged()
    }

//    fun resetBitmaps(list:ArrayList<Bitmap> ){
//        mBitmaps.addAll(list)
//        notifyDataSetChanged()
//    }


    private inner class TrimmerViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var thumbImageView: ImageView

        init {
            thumbImageView = itemView.findViewById(R.id.thumb)
            val layoutParams = thumbImageView.layoutParams as LinearLayout.LayoutParams
            layoutParams.width = (context.resources.displayMetrics.widthPixels - dpToPx(35) * 2)/VideoTrimView.MAX_COUNT_RANGE
            thumbImageView.layoutParams = layoutParams
        }
    }


    fun dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density + 0.5f).toInt()
    }

}
