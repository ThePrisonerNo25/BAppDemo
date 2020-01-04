package com.example.yangchaoming.bappdemo.demo1.message_detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.DynamicCircleContract
import com.example.yangchaoming.bappdemo.demo1.DynamicCirclePresenter
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleBean
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean
import com.example.yangchaoming.bappdemo.demo1.widget.*

class DynamicCircleDetailActivity : AppCompatActivity(), DynamicCircleContract.View {
    private lateinit var memberAvatar :ImageView
    private lateinit var memberName :TextView
    private lateinit var tipType :TextView
    private lateinit var tipImg :ImageView
    private lateinit var tvContent :TextView
    private lateinit var nineGridView :NineGridView
    private lateinit var tvAddress :TextView
    private lateinit var tvTime :TextView
    private lateinit var ivCommentLogo :ImageView
    private lateinit var likeContainer :LikesWrapContainer
    private lateinit var commentView :CommentViewGroup
    private lateinit var llLikes :LinearLayout
    private lateinit var llComments :LinearLayout
    private lateinit var simpleEdit :SimpleEditView

    private lateinit var mRequestOptions :RequestOptions
    private lateinit var mDrawableTransitionOptions :DrawableTransitionOptions
    private lateinit var mCommentOrPraisePopupWindow: CommentOrPraisePopupWindow

    private var isReply:Boolean =false
    private var commentItemIndex:Int =-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_detail)

        initView()
        val presenter = DynamicCirclePresenter(this, this)
        presenter.getListData()
    }

    private fun initView() {
         memberAvatar = findViewById<ImageView>(R.id.member_avatar)
         memberName = findViewById<TextView>(R.id.member_name)
         tipType = findViewById<TextView>(R.id.tip_type)
         tipImg = findViewById<ImageView>(R.id.tip_img)
         tvContent = findViewById<TextView>(R.id.content)
         nineGridView = findViewById<NineGridView>(R.id.nine_grid_view)
         tvAddress = findViewById<TextView>(R.id.tv_address)
         tvTime = findViewById<TextView>(R.id.tv_time)
         ivCommentLogo = findViewById<ImageView>(R.id.iv_comment_logo)
         likeContainer = findViewById<LikesWrapContainer>(R.id.like_container)
         commentView = findViewById<CommentViewGroup>(R.id.comment_view)
         llLikes = findViewById<LinearLayout>(R.id.ll_likes)
         llComments = findViewById<LinearLayout>(R.id.ll_comment)
        simpleEdit = findViewById(R.id.simple_edit)
        simpleEdit.setActivityContext(this)

        mCommentOrPraisePopupWindow= CommentOrPraisePopupWindow(this)
        mRequestOptions= RequestOptions().centerCrop()
        mDrawableTransitionOptions= DrawableTransitionOptions.withCrossFade()

    }

    override fun showData(list: ArrayList<DynamicCircleBean>) {
        val bean = list[2]
        memberName.text=bean.name?:" "
        tipType.text=bean.clockTypeStr
        tvContent.text=bean.content
        tvAddress.text=bean.site
        tvTime.text=bean.createTime

//        Log.e(tag,"onBindViewHolder")

        likeContainer.setDatas(bean.likeHeadUrlList)

        nineGridView.setAdapter(NineImageAdapter(this,mRequestOptions,mDrawableTransitionOptions, bean.photoUrlList))

        commentView.setDatas(bean.commentList
                ?: ArrayList<DynamicCircleCommentBean>())

//        nineGridView.setOnImageClickListener{childPos:Int,v: View ->dynamicCircleListener?.imageClick(childPos,p1,v) }
        commentView.listener=object : CommentViewGroup.CommentItemListener{
            override fun itemClick(childPos: Int, v: View) {
//                dynamicCircleListener?.commentReplyClick(childPos,p1,v)
                isReply=true
                commentItemIndex=childPos
                simpleEdit.showPanel()
                var name: String? = bean?.commentList?.get(childPos)?.name
                simpleEdit.setEditHintText(name)
            }
        }

        ivCommentLogo.setOnClickListener{v->
            run {
                //                Log.e(tag,"pos----------$p1")
                mCommentOrPraisePopupWindow.setCurrentPosition(0);
                mCommentOrPraisePopupWindow.showPopupWindow(v)

            }
        }


        mCommentOrPraisePopupWindow.setOnPraiseOrCommentClickListener(object : CommentOrPraisePopupWindow.OnPraiseOrCommentClickListener{
            override fun onCommentClick(position: Int) {
//                dynamicCircleListener?.commentAddClick(position)
                isReply=false
                commentItemIndex=-1
                simpleEdit.showPanel()
                simpleEdit.setEditHintText(bean.name)
            }

            override fun onPraiseClick(position: Int) {
//                dynamicCircleListener?.likesClick(position)

            }
        })

        simpleEdit.editPanelListenter=(object : SimpleEditView.EditPanelListener{
            override fun commitMessage(mesagge: String?) {
               if(isReply){
                Toast.makeText(this@DynamicCircleDetailActivity,"commentItemIndex =$commentItemIndex",Toast.LENGTH_SHORT).show()

               }else{
                   Toast.makeText(this@DynamicCircleDetailActivity,"commentItemIndex =$commentItemIndex",Toast.LENGTH_SHORT).show()
               }
            }
        })

        llLikes.visibility=if(bean.likeHeadUrlList==null||bean.likeHeadUrlList?.size==0) View.GONE else View.VISIBLE
        llComments.visibility=if(bean.commentList==null||bean.commentList?.size==0) View.GONE else View.VISIBLE
    }
}