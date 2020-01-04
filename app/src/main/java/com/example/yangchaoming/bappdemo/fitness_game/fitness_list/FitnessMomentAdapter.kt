package com.example.yangchaoming.bappdemo.fitness_game.fitness_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.fitness_game.bean.FileBean
import com.example.yangchaoming.bappdemo.fitness_game.bean.FitnessMomentBean
import com.example.yangchaoming.bappdemo.widget.NineImageAdapter
import kotlinx.android.synthetic.main.item_fitness_moment.view.*

class FitnessMomentAdapter (val context:Context , var list:ArrayList<FitnessMomentBean>) : RecyclerView.Adapter<FitnessMomentAdapter.Holder>(){
    private var mRequestOptions = RequestOptions().centerCrop()
    private var mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade()
    fun resetData(_list: ArrayList<FitnessMomentBean>){
        list = _list;
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_fitness_moment,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val bean = list[position]
//        holder.avatar

        holder.name.text = bean.name
        holder.timeAndAdress.text = "${bean.createTime} ${bean.cpAreaName}"
        holder.ivWatch.visibility = if(bean.hasFocus == true) View.GONE else View.VISIBLE
        holder.ivUnwatch.visibility = if(bean.hasFocus == true) View.VISIBLE else View.GONE
        holder.tvContent.text = bean.content
        holder.tvCommentNum.text = "${(bean.commentCount ?: 0)}"
        holder.tvLikeNum.text= "${(bean.likeCount ?: 0)}"

        holder.llComment.setOnClickListener {
            listener?.commentClick(it,position)
        }
        holder.llLikes.setOnClickListener {
            listener?.likesClick(it,position)
        }
        holder.ivWatch.setOnClickListener{
            listener?.watchClick(it,position)
        }
        holder.ivUnwatch.setOnClickListener{
            listener?.unwatchClick(it,position)
        }

        val mapFileBeanToUrl = mapFileBeanToUrl(bean.fileList)
        holder.gridView.setAdapter(NineImageAdapter(context,mRequestOptions,mDrawableTransitionOptions,mapFileBeanToUrl))
        holder.gridView.setOnImageClickListener{childPos:Int,v:View ->listener?.iamgeClick(v,childPos,position) }
    }


    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.avatar
        val name = itemView.tv_name
        val timeAndAdress = itemView.tv_time_address
        val ivWatch = itemView.iv_watch
        val ivUnwatch = itemView.iv_unwatch
        val tvContent = itemView.tv_content
        val gridView = itemView.nine_grid_view
        val llComment = itemView.ll_comment
        val tvCommentNum = itemView.tv_comment_num

        val llLikes = itemView.ll_likes
        val tvLikeNum = itemView.tv_like_num


    }

    private fun mapFileBeanToUrl(source: ArrayList<FileBean>?):ArrayList<String>{
        val list= ArrayList<String>()
        source?.forEach {
            if(it.type == 0){ // 图片
                list.add(it.url)
            }else if(it.type == 1){// 视频
                list.add(it.url+"视频")
            }
        }
        return list
    }

    interface Listener{
        fun watchClick(v:View,position: Int)
        fun unwatchClick(v:View,position: Int)
        fun commentClick(v:View,position: Int)
        fun likesClick(v:View,position: Int)
        fun iamgeClick(v:View,childPosition:Int,parentPosition: Int)
    }
    var listener: Listener? =null
}
