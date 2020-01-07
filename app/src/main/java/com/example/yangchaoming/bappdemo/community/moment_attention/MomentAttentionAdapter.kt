package com.example.yangchaoming.bappdemo.community.moment_attention

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.bean.FileBean
import com.example.yangchaoming.bappdemo.community.bean.MomentBean
import com.example.yangchaoming.bappdemo.widget.NineImageAdapter
import kotlinx.android.synthetic.main.item_moment_attention.view.*

class MomentAttentionAdapter (val context: Context,var list:ArrayList<MomentBean>):RecyclerView.Adapter<MomentAttentionAdapter.Holder>(){
    private var mRequestOptions = RequestOptions().centerCrop()
    private var mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade()
    fun resetData(_list:ArrayList<MomentBean>){
        list= _list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_moment_attention,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //address (string, optional): 动态发布的地址 ,
//canDel (boolean, optional): 当前动态能否删除(true说明是自己，也可用于隐藏关注按钮) ,
//clockId (string, optional): 动态id ,
//clockType (integer, optional): 打卡类型标签 0 训练打卡 1饮食打卡 2普通动态 ,
//clockTypeStr (string, optional): 打卡类型标签文字 ,
//commentCount (integer, optional): 评论数量 ,
//content (string, optional): 动态文字内容 ,
//coverFile (文件item, optional): 封面图文件 ,
//createTime (string, optional): 动态发布时间 yyyy-MM-dd HH:mm ,
//fileList (Array[文件item], optional): 动态的照片/视频绝对地址集合 ,
//hasFocus (boolean, optional): 是否已关注 ,
//hasLike (boolean, optional): 当前用户是否已点过赞 ,
//headUrl (string, optional): 头像图片绝对路径(http://xx..) ,
//lastCommentTime (string, optional): 最后评论/回复时间 yyyy-MM-dd HH:mm ,
//likeCount (integer, optional),
//name (string, optional): 姓名 ,
//userId (string, optional): 发动态的作者id ,
//userType (integer, optional): 发动态的用户类型 0:c小程序用户 1:bapp教练 2:capp用户
        val bean = list[position]

        holder.name.text = bean.name
        holder.timeAndAdress.text = "${bean.createTime}"
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

    private fun mapFileBeanToUrl(source: ArrayList<FileBean>?):ArrayList<String>{
        val list= ArrayList<String>()
        source?.forEach {
            if(it.type == 0){ // 图片
              if(!it.url.isNullOrEmpty()) list.add(it.url!!)
            }else if(it.type == 1){// 视频
                if(!it.url.isNullOrEmpty())  list.add(it.url+"视频")
            }
        }
        return list
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.avatar
        val name = itemView.tv_name
        val timeAndAdress = itemView.tv_time_address
        val ivWatch = itemView.iv_watch
        val ivUnwatch = itemView.iv_unwatch
        val tvContent = itemView.tv_content
        val gridView = itemView.nine_grid_view

        val tvLocation = itemView.tv_location
        val llComment = itemView.ll_comment
        val llLikes = itemView.ll_likes
        val tvCommentNum = itemView.tv_comment_num
        val tvLikeNum = itemView.tv_likes_num
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
