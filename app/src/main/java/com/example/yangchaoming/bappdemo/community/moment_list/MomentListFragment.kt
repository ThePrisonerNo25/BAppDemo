package com.example.yangchaoming.bappdemo.community.moment_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.community.CommunityMainContract
import com.example.yangchaoming.bappdemo.community.CommunityMainPresenter
import com.example.yangchaoming.bappdemo.utils.CommonUtil
import com.example.yangchaoming.bappdemo.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_moment_list.*

class MomentListFragment : Fragment(), CommunityMainContract.View {
    lateinit var presenter: CommunityMainPresenter
    lateinit var adapter: MomentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CommunityMainPresenter(requireContext(),this)
        adapter= MomentListAdapter(requireContext(), ArrayList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_moment_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
        recycler_view.addItemDecoration(GridSpacingItemDecoration(2,CommonUtil.dp2px(6f,requireContext()),false))
        recycler_view.adapter=adapter


//        presenter
    }
}