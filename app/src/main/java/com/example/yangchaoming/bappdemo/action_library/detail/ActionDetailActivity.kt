package com.example.yangchaoming.bappdemo.action_library.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionDetailBean

class ActionDetailActivity : AppCompatActivity(), ActionDetailContract.View {


    lateinit var ivBack:ImageView
    lateinit var tv_action_name:TextView
    lateinit var tv_type:TextView
    lateinit var tv_muscle:TextView
    lateinit var tv_other_muscle:TextView
    lateinit var tv_device:TextView
    lateinit var recyclerView:RecyclerView
    lateinit var iv_muscle1:ImageView
    lateinit var iv_muscle2:ImageView

    lateinit var presenter: ActionDetailPresenter
    lateinit var adapter: ActionDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_detail)
        presenter=ActionDetailPresenter(this,this)
        initView()
    }

    private fun initView() {
        ivBack=findViewById(R.id.iv_back)
        tv_action_name=findViewById(R.id.tv_action_name)
        tv_type=findViewById(R.id.tv_type)
        tv_muscle=findViewById(R.id.tv_muscle)
        tv_other_muscle=findViewById(R.id.tv_other_muscle)
        tv_device=findViewById(R.id.tv_device)
        recyclerView=findViewById(R.id.recyclerView)
        iv_muscle1=findViewById(R.id.iv_muscle1)
        iv_muscle2=findViewById(R.id.iv_muscle2)

        adapter= ActionDetailAdapter(this, ArrayList())
        recyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerView.adapter=adapter

        presenter.getDetailInfo()

        ivBack.setOnClickListener { finish() }
    }

    override fun showDetail(mockDetailInfo: ActionDetailBean) {
        if(mockDetailInfo.actionTips.isNullOrEmpty())adapter.resetData(mockDetailInfo.actionTips)

        tv_action_name.text=mockDetailInfo.name
        tv_type.text=mockDetailInfo.actTypeStr
        tv_muscle.text=mockDetailInfo.trainPartName
        tv_other_muscle.text=mockDetailInfo.otherMuscles
        tv_device.text=mockDetailInfo.equipmentSortStr
//        iv_muscle1.setImageResource() mockDetailInfo.muscleImgList
//                iv_muscle2.setImageResource() mockDetailInfo.muscleImgList
    }
}