package com.example.yangchaoming.bappdemo.demo1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleBean
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean
import com.example.yangchaoming.bappdemo.demo1.widget.CommentOrPraisePopupWindow
import com.example.yangchaoming.bappdemo.demo1.widget.CommentViewGroup
import com.example.yangchaoming.bappdemo.demo1.widget.NineImageAdapter
import kotlinx.android.synthetic.main.item_dynamic_circle.view.*



class DynamicCircleAdapter(val context: Context,var list:ArrayList<DynamicCircleBean>) : RecyclerView.Adapter<DynamicCircleAdapter.ViewHolder>() {
     val tag :String ="DynamicCircleAdapter"
    private var mRequestOptions = RequestOptions().centerCrop()
    private var mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade()

    private var mCommentOrPraisePopupWindow: CommentOrPraisePopupWindow = CommentOrPraisePopupWindow(context)
    fun resetData(list_:ArrayList<DynamicCircleBean>){
        list=list_;
//        Log.e(tag,list.toString())
        notifyDataSetChanged()
    }

    fun updateItemChanged(pos:Int){
        notifyItemChanged(pos)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DynamicCircleAdapter.ViewHolder {

        val view= LayoutInflater.from(context).inflate(R.layout.item_dynamic_circle,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: DynamicCircleAdapter.ViewHolder, p1: Int) {
        //会员动态item {
//    clockId (string, optional): 动态信息id ,
//    clockType (integer, optional): 打卡类型 ,
//    clockTypeStr (string, optional): 打卡类型文字 ,
//    commentList (Array[评论信息item], optional): 评论信息集合 ,
//    content (string, optional): 动态内容 ,
//    createTime (string, optional): 动态发布时间 yyyy-MM-dd HH:mm ,
//    headUrl (string, optional): 会员头像图片绝对路径(http://xx.. 前端不用拼域名) ,
//    likeHeadUrlList (Array[string], optional): 点赞头像图片绝对地址集合(http://xx..前端不用拼域名) ,
//    memberId (string, optional): bapp会员id (跳会员详情用这个) ,
//    name (string, optional): 会员姓名 ,
//    photoUrlList (Array[string], optional): 动态照片_绝对地址集合(http://xx..前端不用拼域名) ,
//    site (string, optional): 发布评论时的地点名称 ,
//    wxUserId (string, optional): 小程序用户id (跳粉丝详情用这个)
//}
        val bean = list.get(p1)
        p0.memberName.text=bean.name?:" "
        p0.tipType.text=bean.clockTypeStr
        p0.tvContent.text=bean.content
        p0.tvAddress.text=bean.site
        p0.tvTime.text=bean.createTime

//        Log.e(tag,"onBindViewHolder")

        p0.likeContainer.setDatas(bean.likeHeadUrlList)

        p0.nineGridView.setAdapter(NineImageAdapter(context,mRequestOptions,mDrawableTransitionOptions, bean.photoUrlList))

        p0.commentView.setDatas(bean.commentList
                    ?: ArrayList<DynamicCircleCommentBean>())

        p0.nineGridView.setOnImageClickListener{childPos:Int,v:View ->dynamicCircleListener?.imageClick(childPos,p1,v) }
        p0.commentView.listener=object : CommentViewGroup.CommentItemListener{
            override fun itemClick(childPos: Int, v: View) {
                dynamicCircleListener?.commentReplyClick(childPos,p1,v)
            }
        }

        p0.ivCommentLogo.setOnClickListener{v->
            run {
//                Log.e(tag,"pos----------$p1")
                mCommentOrPraisePopupWindow.setCurrentPosition(p1);
                mCommentOrPraisePopupWindow.showPopupWindow(v)
            }
        }


        mCommentOrPraisePopupWindow.setOnPraiseOrCommentClickListener(object : CommentOrPraisePopupWindow.OnPraiseOrCommentClickListener{
            override fun onCommentClick(position: Int) {
                dynamicCircleListener?.commentAddClick(position)
            }

            override fun onPraiseClick(position: Int) {
                dynamicCircleListener?.likesClick(position)
            }

        })
        p0.llLikes.visibility=if(bean.likeHeadUrlList==null||bean.likeHeadUrlList?.size==0) View.GONE else View.VISIBLE
        p0.llComments.visibility=if(bean.commentList==null||bean.commentList?.size==0) View.GONE else View.VISIBLE
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val viewRoot=view
        val member_avatar = view.member_avatar!!
        val memberName = view.member_name!!
        val tipType = view.tip_type!!
        val tipImg = view.tip_img!!
        val tvContent = view.content!!
        val nineGridView = view.nine_grid_view!!
        val tvAddress = view.tv_address!!
        val tvTime = view.tv_time!!
        val ivCommentLogo = view.iv_comment_logo!!
        val likeContainer = view.like_container!!
        val commentView = view.comment_view!!
        val llLikes = view.ll_likes!!
        val llComments = view.ll_comment!!

    }


    interface DynamicCircleListener{
        fun imageClick(childPos:Int,parentPos:Int,view:View)

        fun commentReplyClick(childPos:Int,parentPos:Int,view:View)

        fun commentAddClick(parentPos: Int)

        fun likesClick(parentPos: Int)
    }

     var dynamicCircleListener: DynamicCircleListener?=null

}