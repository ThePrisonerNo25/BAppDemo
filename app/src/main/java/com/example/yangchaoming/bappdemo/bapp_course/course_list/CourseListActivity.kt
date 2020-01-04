package com.example.yangchaoming.bappdemo.bapp_course.course_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.FitnessCourseBean
import kotlinx.android.synthetic.main.activity_courselist.*

class CourseListActivity : AppCompatActivity(), CourseListContract.View {


    lateinit var mAdapter : CourseListAdapter
    lateinit var presenter: CoursePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courselist)
        initialData()

        recycler_view.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        mAdapter = CourseListAdapter(this, ArrayList())
        recycler_view.adapter=mAdapter
        presenter.getList()
    }

    fun initialData(){
        presenter = CoursePresenter(this,this)
    }

    override fun showMessage(msg: String?) {

    }

    override fun showLoadingDialog(show: Boolean) {

    }

    override fun getMLifecycle(): Lifecycle {
        return lifecycle
    }

    override fun showCourseList(courseList: ArrayList<FitnessCourseBean>) {
       mAdapter.resetData(courseList)
    }
}