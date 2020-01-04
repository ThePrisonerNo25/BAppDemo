package com.example.yangchaoming.bappdemo.fitness_game.fitness_detail

import androidx.lifecycle.Lifecycle

interface FitnessMomentDetailConstract {
    interface View{
        fun getMLifecycle():Lifecycle
        fun showMessage(msg:String?)
        fun showLoadingDialog(show:Boolean)

        fun showMomentList()
        fun showLikeSucceed()
        fun showAttention()
    }
}