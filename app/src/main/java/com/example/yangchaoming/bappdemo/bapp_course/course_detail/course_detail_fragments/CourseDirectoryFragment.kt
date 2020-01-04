package com.example.yangchaoming.bappdemo.bapp_course.course_detail.course_detail_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseCommentBean
import com.example.yangchaoming.bappdemo.bapp_course.course_beans.CourseDirectoryItemBean
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.CourseDetailActivity
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.CourseDetailContract
import com.example.yangchaoming.bappdemo.bapp_course.course_detail.CourseDetailPresenter
import kotlinx.android.synthetic.main.fragment_course_directory.*

class CourseDirectoryFragment : Fragment(), CourseDetailContract.View {


    private lateinit var mAdapter: CourseDirectoryAdapter
    private lateinit var presenter: CourseDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = (activity as CourseDetailActivity).id
        presenter = CourseDetailPresenter(requireContext(),this)
        Log.e("onCreate", "onCreate: CourseDirectoryFragment id : $id ");

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_course_directory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recycler_view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mAdapter = CourseDirectoryAdapter(requireContext(), ArrayList())
        recycler_view.adapter = mAdapter

        Log.e("onViewCreated", "onViewCreated: CourseDirectoryFragment");
        presenter.getCourseList()
    }



    override fun showMessage(msg: String?) {

    }

    override fun showLoadingDialog(show: Boolean) {

    }

    override fun getMLifecycle(): Lifecycle = lifecycle

    override fun showTitleList(titles: ArrayList<String>) {

    }

    override fun showCourseIntroduction() {

    }

    override fun showCourseDirectory(list: ArrayList<CourseDirectoryItemBean>) {
        mAdapter.resetData(list)
    }

    override fun showCourseCommentList(list: ArrayList<CourseCommentBean>) {

    }


}