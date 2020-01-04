package com.example.yangchaoming.bappdemo.community.moment_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.bean.MomentBean
import kotlinx.android.synthetic.main.item_moment_type_0.view.*

class MomentListAdapter (val context: Context,var list:ArrayList<MomentBean>): RecyclerView.Adapter<MomentListAdapter.Holder>(){

    companion object{
        const val TYPE_SHORT=0
        const val TYPE_LONG=1
    }

    fun resetData( _list:ArrayList<MomentBean>){
        list = _list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentListAdapter.Holder {
        val view =   if(viewType == TYPE_LONG){
          LayoutInflater.from(context).inflate(R.layout.item_moment_type_0,parent,false)

        }else{
            LayoutInflater.from(context).inflate(R.layout.item_moment_type_1,parent,false)
        }
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MomentListAdapter.Holder, position: Int) {
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
//        holder.iv_cover
        holder.tv_content.text =  bean.content
//        holder.iv_contributor
        holder.tv_contributor.text = bean.name
        holder.tv_like_num.text = (bean.commentCount ?: 0).toString()

        holder.itemView.setOnClickListener{
            listener?.itemClick(it,position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type= if(position%2 == 0) TYPE_SHORT else TYPE_LONG
        return type
    }


    inner class  Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val iv_cover =  itemView.findViewById<ImageView>(R.id.iv_cover)
        val tv_content =   itemView.findViewById<TextView>(R.id.tv_content)
        val iv_contributor =  itemView.findViewById<ImageView>(R.id.iv_contributor)
        val tv_like_num =   itemView.findViewById<TextView>(R.id.tv_like_num)
        val iv_like =   itemView.findViewById<ImageView>(R.id.iv_like)
        val tv_contributor =  itemView.findViewById<TextView>(R.id.tv_contributor)
        val iv_video_play =  itemView.findViewById<ImageView>(R.id.iv_video_play)
    }

    var listener: Listener? =null

    interface Listener{
        fun itemClick(v: View,pos: Int)
    }

}