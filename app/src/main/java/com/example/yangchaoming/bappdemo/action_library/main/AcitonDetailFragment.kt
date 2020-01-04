package com.example.yangchaoming.bappdemo.action_library.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import com.example.yangchaoming.bappdemo.action_library.bean.MenuBean

class AcitonDetailFragment :Fragment(), ActionContract.ActionDetailView {



    lateinit var recyclerView: RecyclerView
    lateinit var presenter: ActionDetailPresenter
    lateinit var adapter: ActionDetailAdapter
    lateinit var btnAdd: ConstraintLayout
    lateinit var acitonDetailFragment:DialogAddCustomAction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ActionDetailPresenter(context!!, this)
         acitonDetailFragment = DialogAddCustomAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val rootView= inflater.inflate(R.layout.fragment_action_detail,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recyclerView)
        btnAdd=view.findViewById(R.id.fab)
        recyclerView.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        adapter= ActionDetailAdapter(presenter.actionList, context!!)
        recyclerView.adapter=adapter


        adapter.itemListener=object : ActionDetailAdapter.ActionDetailListener {
            override fun selected(v: View, pos: Int) {
                presenter.actionList[pos].selected=!presenter.actionList[pos].selected
                adapter.notifyDataSetChanged()
                presenter.updateMenuBeanStatus(presenter.actionList[pos])
               if( activity is ActionMainActivity) {
                   (activity as ActionMainActivity).updateMenuList()
                   (activity as ActionMainActivity).updateTotalView()
               }
            }

            override fun delete(v: View, pos: Int) {
                presenter.actionList[pos].selected=false
                presenter.updateMenuBeanStatus(presenter.actionList[pos])
                presenter.actionList.remove(presenter.actionList[pos])

                adapter.notifyDataSetChanged()
                if( activity is ActionMainActivity) {
                    (activity as ActionMainActivity).updateMenuList()
                    (activity as ActionMainActivity).updateTotalView()
                }
            }

        }


        btnAdd.setOnClickListener {
            acitonDetailFragment.show(fragmentManager!!,"DialogAddCustomAction")
        }

        acitonDetailFragment.listener =object : DialogAddCustomAction.AddCustomActionListener{
            override fun addAction(actionName: String, actionNum: String, actionGroupNum: String,unit:Int) {

            }

        }
    }

    fun setActionDetailData(menuBean: MenuBean) {
        presenter.actionList.clear()
        presenter.currentMenuBean=menuBean
        var adapterType=if(menuBean.id==0) ActionDetailAdapter.CUSTOM_TYPE else ActionDetailAdapter.NORMAL_TYPE
        adapter.viewType=adapterType
        presenter.getActionListByType(adapterType)
    }



    override fun showActionList(actionList: ArrayList<ActionItemBean>) {
        adapter.resetData(actionList)
    }


}