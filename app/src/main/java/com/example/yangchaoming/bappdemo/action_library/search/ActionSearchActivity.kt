package com.example.yangchaoming.bappdemo.action_library.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import com.example.yangchaoming.bappdemo.action_library.main.ActionDetailAdapter

class ActionSearchActivity :AppCompatActivity(), ActionSearchContract.View {


    lateinit var ivBack:ImageView
    lateinit var tvTitle:TextView
    lateinit var tvTips:TextView
    lateinit var recyclerView: RecyclerView
    lateinit var btnConfirm: Button
    lateinit var adapter: ActionDetailAdapter
    lateinit var presenter: ActionSearchPresenter
    var keyWord=""
    companion object{
        val KEY_WORD="keyword"
        val SELECTED_IDS="selectedIds"
        val SEARCH_ITEM="searchItem"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_search)
        presenter=ActionSearchPresenter(this,this)

        if(intent.hasExtra(KEY_WORD))keyWord = intent.getStringExtra(KEY_WORD)

        if(intent.hasExtra(SELECTED_IDS)) presenter.selectedIds.addAll(intent.getIntegerArrayListExtra(SELECTED_IDS))


        initView()
    }

    private fun initView() {
        ivBack= findViewById(R.id.iv_back)
        tvTitle= findViewById(R.id.tv_title)
        tvTips= findViewById(R.id.tv_tips)
        recyclerView= findViewById(R.id.recyclerView)
        btnConfirm= findViewById(R.id.btn_confirm)

        recyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter= ActionDetailAdapter(presenter.actionList,this)
        adapter.viewType=ActionDetailAdapter.NORMAL_TYPE
        recyclerView.adapter=adapter

        adapter.itemListener=object: ActionDetailAdapter.ActionDetailListener{
            override fun selected(v: View, pos: Int) {
                presenter.actionList[pos].selected=!presenter.actionList[pos].selected
            }

            override fun delete(v: View, pos: Int) {

            }

        }

        btnConfirm.setOnClickListener {
            val intent= Intent()
            intent.putExtra(SEARCH_ITEM,presenter.actionList)
            setResult(Activity.RESULT_OK)
            finish()
        }

        ivBack.setOnClickListener { finish() }

        presenter.getActionList("")
    }

    override fun showActionList(actionList: ArrayList<ActionItemBean>) {
        adapter.resetData(actionList)

        tvTips.text="为您搜索出${actionList.size}个相关结果"
    }


}