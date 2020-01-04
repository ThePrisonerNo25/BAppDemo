package com.example.yangchaoming.bappdemo.action_library.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import com.example.yangchaoming.bappdemo.action_library.bean.ActionWrapFilterBean
import com.example.yangchaoming.bappdemo.action_library.bean.MenuBean
import com.example.yangchaoming.bappdemo.action_library.search.ActionSearchActivity


class ActionMainActivity : AppCompatActivity(), ActionContract.View {


    lateinit var presenter: ActionPresenter
    lateinit var ivBack:ImageView
    lateinit var tvFilter:TextView
    lateinit var etSearch:EditText
    lateinit var btnSearch:Button
    lateinit var rcMenu:RecyclerView
    lateinit var actionContainer:FrameLayout
    lateinit var tvTotal:TextView
    lateinit var btnConfirm: Button
    //-----------------------侧边栏
    lateinit var drawerLayout: DrawerLayout
    lateinit var clRight:ConstraintLayout
    lateinit var recyclerViewFilter: RecyclerView
    lateinit var adapterFilter: ActionFilterAdapter

    lateinit var btn_filter_cancel: Button
    lateinit var btn_filter_ok: Button


    lateinit var detailFragment: AcitonDetailFragment

    companion object{
       val REQUEST_CODE=102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_main)
        presenter= ActionPresenter(this, this)

        initView()
        detailFragment = AcitonDetailFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.action_container, detailFragment).commit()
        presenter.start()
    }

    private fun initView() {
        ivBack=findViewById(R.id.iv_back)
        tvFilter=findViewById(R.id.tv_filter)
        etSearch=findViewById(R.id.et_search)
        btnSearch=findViewById(R.id.btn_search)
        rcMenu=findViewById(R.id.menu)
        actionContainer=findViewById(R.id.action_container)
        tvTotal=findViewById(R.id.tv_total)
        btnConfirm=findViewById(R.id.btn_confirm)


        rcMenu.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rcMenu.adapter= MenuAdapter(ArrayList(), this)

        (rcMenu.adapter as MenuAdapter).menuList=object : MenuAdapter.MenuListener {
            override fun itemClick(v: View, pos: Int) {
                presenter.menuItemList.forEach { it.selector=false }
                presenter.menuItemList[pos].selector=true
                rcMenu.adapter?.notifyDataSetChanged()
                showActionDetail(presenter.menuItemList[pos])
            }
        }


        btnSearch.setOnClickListener { _ ->run {
            val text = etSearch.text
            if(text.isNullOrBlank()) return@setOnClickListener
           val intent= Intent(this@ActionMainActivity,ActionSearchActivity::class.java)
            intent.putExtra(ActionSearchActivity.KEY_WORD,text.toString())
           val selectedIds= presenter.getSelectedIdList()
            intent.putExtra(ActionSearchActivity.SELECTED_IDS,selectedIds)

            startActivityForResult(intent,REQUEST_CODE)

            etSearch.editableText.clear()

        } }

        //----------------------------------------侧边栏--------------------------------------

        drawerLayout= findViewById(R.id.drawerLayout)
        clRight= findViewById(R.id.cl_right)
        recyclerViewFilter= findViewById(R.id.recycler_view_filter)

        btn_filter_cancel= findViewById(R.id.btn_filter_cancel)
        btn_filter_ok= findViewById(R.id.btn_filter_ok)

        adapterFilter= ActionFilterAdapter(this,ArrayList())

        recyclerViewFilter.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)


        recyclerViewFilter.adapter=adapterFilter


        tvFilter.setOnClickListener {
            drawerLayout.openDrawer(clRight)
        }

        adapterFilter.listener=object : ActionFilterAdapter.ActionFilterListener{
            override fun itemClick(childView: View, parentView: View, childPos: Int, parentPos: Int) {
                val bean = presenter.filterList[parentPos].list[childPos]

                Toast.makeText(this@ActionMainActivity, bean.toString(), Toast.LENGTH_SHORT).show();
                //选项标识 0：可选 1：不可选 2：当前选中


            }

            override fun arrowClick(view: View, parentPos: Int) {
                presenter.filterList[parentPos].expand=true
                adapterFilter.notifyDataSetChanged()
            }

        }

        drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerOpened(drawerView: View) {
                presenter.getFilterDatas()
            }

        })



    }

    override fun showMenuList(menuItemList: ArrayList<MenuBean>) {
        (rcMenu.adapter as MenuAdapter).resetData(menuItemList)
    }

    override fun showActionDetail(menuBean: MenuBean) {
        detailFragment.setActionDetailData(menuBean)
    }

    override fun showActionFilter(list: ArrayList<ActionWrapFilterBean>) {
//        if(list==null){
//            Toast.makeText(this, "获取筛选条件选项失败", Toast.LENGTH_SHORT).show();
//            return
//        }

        adapterFilter.resetData(list)

    }

    /**
     * 更新MenuList
     */
    fun updateMenuList(){
        rcMenu.adapter?.notifyDataSetChanged()
    }

    /**
     * 更新选中的总数
     */
    fun updateTotalView(){
        tvTotal.text="共选（${presenter.getTotalItemSelected()}）"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==REQUEST_CODE){
               val searchResult= data?.getParcelableArrayListExtra<ActionItemBean>(ActionSearchActivity.SEARCH_ITEM)
                presenter.remapMenu(searchResult)
                updateMenuList()
                updateTotalView()
            }
        }
    }
}
