package com.example.yangchaoming.bappdemo.demo1.widget

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicUtil
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean
import java.util.ArrayList

class CommentViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), ViewGroup.OnHierarchyChangeListener {


    var tag ="CommentViewGroup"
    private val mContext: Context = context
    private var COMMENT_TEXT_POOL: SimpleWeakObjectPool<View> = SimpleWeakObjectPool()
    private var mCommentBeans: List<DynamicCircleCommentBean>? = null
    init {
        setOnHierarchyChangeListener(this)
    }


    fun setDatas( commentBeans: List<DynamicCircleCommentBean>){
        this.mCommentBeans = commentBeans
        if (commentBeans != null) {
            val oldCount = childCount
            val newCount = commentBeans.size
            if (oldCount > newCount) {
                removeViewsInLayout(newCount, oldCount - newCount)
            }
            for (i in 0 until newCount) {
                val hasChild = i < oldCount
                var childView: View? = if (hasChild) getChildAt(i) else null
                val commentBean = commentBeans.get(i)
                if (childView == null) {
//                    Log.e(tag,"添加评论item")
                    childView = COMMENT_TEXT_POOL.get()
                    //添加评论item , 如果COMMENT_TEXT_POOL中有，则取出来进行修改，添加到ViewGroup中  没有则 创建新的
                    addView(createCommentItem(commentBean,childView,i))

                } else {
//                    Log.e(tag,"修改评论item内容")
                    //修改评论item内容 （不需要添加 原ViewGroup存在现有的，直接修改）
                    createCommentItem(commentBean,childView,i)
                }
            }
            requestLayout()
        }

    }

    private fun createCommentItem(bean: DynamicCircleCommentBean,view_:View?,postion:Int):View {
        var view:View?=null
        when(view_){
            null -> view = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_circle_comment, this, false)
            else -> view = view_
        }
        var ivCommentAvatar= view!!.findViewById<ImageView>(R.id.iv_comment_avatar)
        val tvCommentName=view!!.findViewById<TextView>(R.id.tv_comment_name)
        val tvCommentTime=view!!.findViewById<TextView>(R.id.tv_comment_time)
        val tvReplyContent=view!!.findViewById<TextView>(R.id.tv_reply_content)
//        p0.iv_comment_avatar
        ivCommentAvatar.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_launcher))
        tvCommentName.text = bean.name?:" ";

        val formatDay = DynamicUtil.getFormatDay(bean.createTime ?: "")
        tvCommentTime.text=formatDay

        if(TextUtils.isEmpty(bean.replyToName)){
            tvReplyContent.text=bean.content
        }else{
            val content="回复"+bean.replyToName+":"+bean.content
            val spannableString = SpannableString(content)
            val colorSpan = ForegroundColorSpan(Color.parseColor("#f5947157"))
            spannableString.setSpan(colorSpan, 2,bean.replyToName!!.length+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            tvReplyContent.text=spannableString
        }

        view.setOnClickListener{v->listener?.itemClick(postion,v)}
        return view
    }

    interface CommentItemListener{
        fun itemClick(childPos:Int,v:View)
    }

    var listener :CommentItemListener?=null

    override fun onChildViewRemoved(parent: View?, child: View?) {
        COMMENT_TEXT_POOL.put(child)
    }

    override fun onChildViewAdded(parent: View?, child: View?) {
    }

}