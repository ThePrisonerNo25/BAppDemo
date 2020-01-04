package com.example.yangchaoming.bappdemo.demo1.message_list

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.demo1.bean.DynamicCircleMessageBean

class DynamicCircleMessagesActivity :Activity(),DynamicCircleMessageContract.View{


    private lateinit var ivBack:ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DynamicCircleMessagesAdapter
    private lateinit var presenter:DynamicCircleMessagePresenter
    init {
        presenter = DynamicCircleMessagePresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_messages)

        initView()
    }

    private fun initView() {
        ivBack=  findViewById(R.id.iv_back)
        recyclerView =findViewById(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter = DynamicCircleMessagesAdapter(this, ArrayList())
        recyclerView.adapter=adapter
        presenter.getMessageList()

    }

    override fun showDataList(list: List<DynamicCircleMessageBean>) {
        adapter.resetData(list)
    }
}