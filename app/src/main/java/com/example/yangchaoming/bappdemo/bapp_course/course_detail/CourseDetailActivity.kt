package com.example.yangchaoming.bappdemo.bapp_course.course_detail

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseCommentBean
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseDirectoryItemBean
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments.CourseCommentAdapter
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments.CourseCommentFragment
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments.CourseDirectoryFragment
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments.CourseIntroductionFragment
import kotlinx.android.synthetic.main.activity_course_detail.*

class CourseDetailActivity : AppCompatActivity(), CourseDetailContract.View {



    lateinit var presenter: CourseDetailPresenter

    var id = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        initialData()


    }

    private fun initialData(){
        id ="initialData"
        presenter = CourseDetailPresenter(this,this)
        presenter.mockData()

    }

    override fun showTitleList(titles: ArrayList<String>) {
        Log.e("showTitleList", "showTitleList: $titles");
//        titles.forEach{ tab_layout.addNewTab(it)}
//        initTabs(titles)
        val arrayListOf = arrayListOf<Fragment>(CourseIntroductionFragment(), CourseDirectoryFragment(), CourseCommentFragment())

        val adapter = CourseDetailPagerAdapter(supportFragmentManager, arrayListOf,titles)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                
            }

            override fun onPageSelected(position: Int) {
                Log.e("onPageSelected", "onPageSelected: $position");
            }

        })

//        tab_layout.setViewPager(view_pager,titles.toTypedArray())
    }

    private fun initTabs(titles:ArrayList<String>){
        Log.e("initTabs", "initTabs: ");
        titles.forEach {
            val newTab = tab_layout.newTab()
            Log.e("initTabs", "initTabs: $it");
            newTab.text = it
            tab_layout.addTab(newTab)
        }
    }

    override fun showCourseIntroduction() {
    }

    override fun showCourseDirectory(list: ArrayList<CourseDirectoryItemBean>) {
    }

    override fun showCourseCommentList(list: ArrayList<CourseCommentBean>) {

    }


    override fun showMessage(msg: String?) {
    }

    override fun showLoadingDialog(show: Boolean) {
    }

    override fun getMLifecycle(): Lifecycle =lifecycle


}