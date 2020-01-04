package com.example.yangchaoming.bappdemo.action_library.main

import com.example.yangchaoming.bappdemo.action_library.bean.ActionItemBean
import com.example.yangchaoming.bappdemo.action_library.bean.ActionWrapFilterBean
import com.example.yangchaoming.bappdemo.action_library.bean.MenuBean

interface ActionContract {
    interface View{ //动作库 主页面
        fun showMenuList(menuItemList: ArrayList<MenuBean>) //显示动作侧边列表
        fun showActionDetail(menuBean: MenuBean) //显示具体的动作
        fun showActionFilter(filterBean: ArrayList<ActionWrapFilterBean>) //显示筛选条件
    }

    interface ActionDetailView{ //动作库 具体列表页
        fun showActionList(actionList: ArrayList<ActionItemBean>) //显示动作列表
    }

}