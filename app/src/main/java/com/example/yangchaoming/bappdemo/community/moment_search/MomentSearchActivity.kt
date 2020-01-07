package com.example.yangchaoming.bappdemo.community.moment_search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.moment_list.MomentListFragment
import com.example.yangchaoming.bappdemo.community.moment_follower_list.MomentFollowerListFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.acitity_moment_search.*

class MomentSearchActivity : AppCompatActivity(){
    private var fragments = ArrayList<Fragment>()
    private var titles = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_moment_search)
        initializeData()
        sliding_tab_layout.addNewTab("动态")
        sliding_tab_layout.addNewTab("用户")

        et_search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val inputString = s?.toString()
                tv_search.text = inputString
                cl_search_label.visibility = if(!inputString.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    performSearch()

                    return true
                }
                return false
            }
        })

        iv_clear.setOnClickListener{
            et_search.text.clear()
            setSearchResultVisibility(false)
        }


        val pagerAdapter = SearchPagerAdapter(fragments, titles, supportFragmentManager)
        view_pager.adapter = pagerAdapter
        sliding_tab_layout.setViewPager(view_pager)

        sliding_tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

            }

            override fun onTabReselect(position: Int) {

            }

        })

    }

    private fun initializeData(){
        fragments.add(MomentListFragment())
        fragments.add(MomentFollowerListFragment())
        titles.add("动态")
        titles.add("用户")
    }

    private fun  performSearch(){

    }

    /**
     * 显示搜索结果
     *
     * @param resultShow 是否显示
     */
    private fun setSearchResultVisibility(resultShow:Boolean){
        cl_search_label.visibility = if(resultShow) View.INVISIBLE else View.VISIBLE
        sliding_tab_layout.visibility = if(resultShow) View.VISIBLE else View.INVISIBLE
        view_pager.visibility = if(resultShow) View.VISIBLE else View.INVISIBLE
    }
}