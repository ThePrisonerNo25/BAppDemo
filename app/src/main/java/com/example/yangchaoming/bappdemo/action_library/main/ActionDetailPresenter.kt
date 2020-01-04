package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import com.example.yangchaoming.bappdemo.action_library.bean.MenuBean

class ActionDetailPresenter (val context:Context ,val view: ActionContract.ActionDetailView){
    var currentMenuBean:MenuBean?=null //当前动作列表的根（持有选中动作item的id）
    var actionList:ArrayList<ActionItemBean> = ArrayList()

    fun getActionListByType(parentType:Int){

        mockDetailAction(parentType)
        mapItemSelected(actionList)

        view.showActionList(actionList)
    }

    fun mockDetailAction(parentType: Int) {

//        actGroup (string, optional): 动作组完整文字 ,
//        actGroupNum (string, optional): 动作组_组数 ,
//        actNum (string, optional): 动作组_次数/秒数 ,
//        actType (integer, optional): 动作类型 0热身 1训练 2拉伸 ,
//        actUnit (integer, optional): 动作组_单位： 0：次 1：秒 ,
//        id (string, optional): 动作id ,
//        libType (integer, optional): 动作库数据类型(0：系统预置 1：自定义 ,
//        name (string, optional): 动作名称 ,
//        trainPartId (integer, optional): 训练部位id
        when(parentType){
            ActionDetailAdapter.NORMAL_TYPE -> {
                for(i in 0..50){
//                    actionList.add(ActionItemBean("${currentMenuBean?.text} #_$i","$i x ${i%5}",i, i,false))
                    actionList.add(ActionItemBean("actGroup","3","50",i%3,i%2,i.toString(),1,"name $i",i,false))
                }
            }

            ActionDetailAdapter.CUSTOM_TYPE -> {
                for(i in 100..150){
                    actionList.add(ActionItemBean("actGroup","3","50",i%3,i%2,i.toString(),0,"name $i",i,false))
                }
            }
        }
    }

    /**
     * 为Item设置选中状态
     */
    fun mapItemSelected(list:ArrayList<ActionItemBean>){
        if(list.isNullOrEmpty()||currentMenuBean==null) return


        list.forEach(fun(item:ActionItemBean){
            item.selected=currentMenuBean!!.childId.contains(item.id.toInt())
        })
    }

    /**
     * 更新MenuBean的状态
     */
    fun updateMenuBeanStatus(itemBean: ActionItemBean) {
        if(currentMenuBean==null) return
        when(itemBean.selected){
            true -> {
               if(!currentMenuBean!!.childId.contains(itemBean.id.toInt())) currentMenuBean!!.childId.add(itemBean.id.toInt())
            }

            false -> {
                if(currentMenuBean!!.childId.contains(itemBean.id.toInt())) currentMenuBean!!.childId.remove(itemBean.id.toInt())
            }
        }
    }


}