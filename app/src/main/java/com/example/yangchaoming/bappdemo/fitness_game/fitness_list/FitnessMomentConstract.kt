package com.example.yangchaoming.bappdemo.fitness_game.fitness_list

import androidx.lifecycle.Lifecycle

interface FitnessMomentConstract {
    interface View{
        fun getMLifecycle():Lifecycle
        fun showMessage(msg:String?)
        fun showLoadingDialog(show:Boolean)

        fun showMomentList()
        fun showLikeSucceed()
        fun showAttention()
    }
}