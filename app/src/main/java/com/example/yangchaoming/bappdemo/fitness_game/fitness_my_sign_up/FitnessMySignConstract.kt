package com.example.yangchaoming.bappdemo.fitness_game.fitness_my_sign_up

import androidx.lifecycle.Lifecycle

interface FitnessMySignConstract {
    interface View{
        fun getMLifecycle():Lifecycle
        fun showMessage(msg:String?)
        fun showLoadingDialog(show:Boolean)

        fun showSignUpList()

    }
}