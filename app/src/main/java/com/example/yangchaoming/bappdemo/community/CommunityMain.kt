package com.example.yangchaoming.bappdemo.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.moment_attention.MomentAttentionListFragment
import com.example.yangchaoming.bappdemo.community.moment_list.MomentListFragment
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.acitity_community_main.*

class CommunityMain : AppCompatActivity() {
    lateinit var fragments: ArrayList<Fragment>
    lateinit var titles: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_community_main)
        initializeData()

        val pagerAdapter = CommunityMainPagerAdapter(fragments, titles, supportFragmentManager)
        view_pager.adapter = pagerAdapter
        sliding_tab_layout.setViewPager(view_pager)

        sliding_tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                titles.forEachIndexed { index, title ->
                    run{
                        sliding_tab_layout.getTitleView(index).textSize = if(index == position) 30f else 14f
                    }
                }
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    private fun initializeData() {
        fragments.add(MomentListFragment())
        fragments.add(MomentAttentionListFragment())
        titles.add("动态")
        titles.add("用户")
    }
}