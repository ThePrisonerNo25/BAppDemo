package com.example.yangchaoming.bappdemo.demo1

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleBean
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleCommentBean
import com.example.yangchaoming.bappdemo.demo1.widget.EditPanelView



class DynamicCircleFragment : Fragment(),DynamicCircleContract.View{
    private lateinit var presenter: DynamicCirclePresenter
    private lateinit var dynamicCircleAdapter:DynamicCircleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editPanel:EditPanelView
    private var mChildPos:Int =-1
    private var mParentPos:Int =-1
    private var isReply:Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter=  DynamicCirclePresenter(context!!,this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dynamic_circle, container, false)
        recyclerView= view.findViewById<RecyclerView>(R.id.recyclerView);
        editPanel= view.findViewById<EditPanelView>(R.id.edit_panel);
        editPanel.setActivityContext(activity as Activity)

        recyclerView.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        dynamicCircleAdapter = DynamicCircleAdapter(context!!, ArrayList<DynamicCircleBean>())
        recyclerView.adapter= dynamicCircleAdapter
        (recyclerView.itemAnimator as SimpleItemAnimator) .supportsChangeAnimations=false
        dynamicCircleAdapter.dynamicCircleListener=(object :DynamicCircleAdapter.DynamicCircleListener{
            override fun imageClick(childPos: Int, parentPos: Int, view: View) {
                Toast.makeText(context,"childPos# $childPos ------parentPos# $parentPos ",Toast.LENGTH_SHORT).show()
            }

            override fun commentReplyClick(childPos: Int, parentPos: Int, view: View) {
                Toast.makeText(context,"childPos# $childPos ------parentPos# $parentPos ",Toast.LENGTH_SHORT).show()
                isReply=true
                mChildPos=childPos
                mParentPos=parentPos
//                Log.e("editPanel","editPanel ${editPanel.isVisiableForLast}")
                editPanel.showEditPanel()
                editPanel.setEditHintText( presenter.list.get(parentPos).commentList?.get(childPos)?.name)

            }

            override fun commentAddClick(parentPos: Int) {
                Toast.makeText(context,"------parentPos# $parentPos ",Toast.LENGTH_SHORT).show()

                isReply=false
                mChildPos=-1
                mParentPos=parentPos
                editPanel.showEditPanel()
                editPanel.setEditHintText(presenter.list.get(parentPos).name)
            }

            override fun likesClick(parentPos: Int) {
//                Toast.makeText(context,"------parentPos# $parentPos ",Toast.LENGTH_SHORT).show()
                val bean = presenter.list.get(parentPos)
                if(bean.likeHeadUrlList==null){
                    var list= ArrayList<String>();
                    list.add("LikeUrl")
                    bean.likeHeadUrlList=list
                }else{
                    bean.likeHeadUrlList!!.add("LikeUrl")
                }
                dynamicCircleAdapter.updateItemChanged(parentPos)
            }

        })


        editPanel.editPanelListenter=(object : EditPanelView.EditPanelListener{
            override fun commitMessage(mesagge: String?) {
                if(mParentPos==-1||TextUtils.isEmpty(mesagge))return
                if(isReply){
                    val bean = presenter.list.get(mParentPos)
                    bean.commentList!!.add(DynamicCircleCommentBean("commentId#_new","commentMemberId# _new",1,mesagge,
                            "2019-6-4 16:48 ","headUrl _new","评论人姓名 _new","jack _new"))
                    dynamicCircleAdapter.updateItemChanged(mParentPos)

                }else{
                    val bean = presenter.list.get(mParentPos)
                    if(bean.commentList==null){
                        var list= ArrayList<DynamicCircleCommentBean>();
                        list.add(DynamicCircleCommentBean("commentId#_new","commentMemberId# _new",1,mesagge,
                                "2019-6-4 16:4 ","headUrl _new","评论人姓名 _new","jack _new"))
                        bean.commentList=list
                    }else{
                        bean.commentList!!.add(DynamicCircleCommentBean("commentId#_new","commentMemberId# _new",1,mesagge,
                                "2019-6-4 16:40 ","headUrl _new","评论人姓名 _new","jack _new"))
                    }
                    dynamicCircleAdapter.updateItemChanged(mParentPos)

                }
                editPanel.dismiss()

            }

            override fun softKeyBoardShow(mDisplayHeight: Int) {

                try {
                    val view:ViewGroup = recyclerView.layoutManager!!.findViewByPosition(mParentPos) as ViewGroup
                    val lastChild = view.getChildAt(view.childCount - 1)
                    var location = IntArray(2)
                    lastChild!!.getLocationInWindow(location)
//                Log.e("softKeyBoardShow","mDisplayHeight = $mDisplayHeight  location= ${location[1]}")
                    val dy = mDisplayHeight - location[1]-lastChild.height
                    recyclerView.smoothScrollBy(0, -dy)
                } catch (e: Exception) {
                }
            }
        })
        initData()
        return view;
    }




    fun initData(){
        presenter.getListData()
    }

    override fun showData(list: ArrayList<DynamicCircleBean>) {
        dynamicCircleAdapter.resetData(list)
    }
}