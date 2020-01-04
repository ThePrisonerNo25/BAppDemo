package com.example.yangchaoming.bappdemo.action_library.main

import android.content.Context
import android.os.Handler
import com.example.yangchaoming.bappdemo.action_library.bean.*

class ActionPresenter(val view: ActionContract.View, val context: Context ) {
    var menuItemList:ArrayList<MenuBean> =ArrayList<MenuBean>()
    var filterBean:ActionFilterBean?=null
    var filterList:ArrayList<ActionWrapFilterBean> = ArrayList()

    fun start(){
        mockMenu()
        view.showMenuList(menuItemList)
        Handler().postDelayed(Runnable{
            view.showActionDetail(menuItemList[0])
        },500)

    }


    fun getFilterDatas(){
        mockFilter()
        mapFilterData()
        view.showActionFilter(filterList)
    }


    private fun mapFilterData() {
        if(filterBean==null) return
        filterList.clear()

        filterList.add(ActionWrapFilterBean("器械要求",filterBean!!.equipmentOneList,false,0))
        filterList.add(ActionWrapFilterBean("自由器械",filterBean!!.equipmentFreedomList,false,1))
        filterList.add(ActionWrapFilterBean("固定器械",filterBean!!.equipmentFixedList,false,2))
        filterList.add(ActionWrapFilterBean("难度",filterBean!!.actLevelList,false,3))
        filterList.add(ActionWrapFilterBean("类型",filterBean!!.actTypeList,false,4))

    }


    fun mockMenu(isRefresh:Boolean=false){
        menuItemList.add(MenuBean("自定义",0, 1,ArrayList()))
        for(i in 1..10){
            menuItemList.add(MenuBean("menuItem #$i",i,0,ArrayList()))
        }
    }

    fun mockFilter(){
        ////    actLevelList (Array[动作属性选项item], optional): 难度 ,
////    actTypeList (Array[动作属性选项item], optional): 类型 ,
////    equipmentFixedList (Array[动作属性选项item], optional): 固定器械 ,
////    equipmentFreedomList (Array[动作属性选项item], optional): 自由器械 ,
////    equipmentOneList (Array[动作属性选项item], optional): 器械要求

        val actLevelList : ArrayList<ActionFilterItemBean> = ArrayList()
        repeat(5){
            val bean= ActionFilterItemBean(""+it,"初级"+it,0)
            actLevelList.add(bean)
        }

        val actTypeList : ArrayList<ActionFilterItemBean> = ArrayList()
        repeat(5){
            val bean= ActionFilterItemBean(""+it,"类型"+it,0)
            actTypeList.add(bean)
        }

        val equipmentFixedList : ArrayList<ActionFilterItemBean> = ArrayList()
        repeat(12){
            val bean= ActionFilterItemBean(""+it,"固定器械"+it,0)
            equipmentFixedList.add(bean)
        }

        val equipmentFreedomList : ArrayList<ActionFilterItemBean> = ArrayList()
        repeat(12){
            val bean= ActionFilterItemBean(""+it,"自由器械"+it,0)
            equipmentFreedomList.add(bean)
        }

        val equipmentOneList : ArrayList<ActionFilterItemBean> = ArrayList()
        repeat(12){
            val bean= ActionFilterItemBean(""+it,"器械要求"+it,0)
            equipmentOneList.add(bean)
        }

        filterBean=  ActionFilterBean(actLevelList,actTypeList,equipmentFixedList,equipmentFreedomList,equipmentOneList)


    }

    /**
     * 选中的item的总数
     */
    fun getTotalItemSelected(): Int {
        var total=0
        menuItemList.forEach { total += it.childId.size }
        return total
    }

    /**
     * 获取选中id的集合
     */
    fun getSelectedIdList() : ArrayList<Int>{
        var idList=ArrayList<Int>()
        menuItemList.forEach {
           if(it.childId.isNotEmpty()) idList.addAll(it.childId)
        }
        return idList
    }


    fun remapMenu(searchResult: java.util.ArrayList<ActionItemBean>?) {
        if(searchResult.isNullOrEmpty()) return
        searchResult.forEach {
           val bean= menuItemList.find(fun(item):Boolean{
              return  item.id==it.trainPartId
            })
            if(bean!=null) {
                when(it.selected){
                    true -> {
                        if(!bean.childId.contains(it.id.toInt())) bean.childId.add(it.id.toInt())
                    }
                    false -> {
                        if(bean.childId.contains(it.id.toInt())) bean.childId.remove(it.id.toInt())
                    }
                }
            }
        }

    }


}


