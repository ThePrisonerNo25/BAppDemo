package com.example.yangchaoming.bappdemo.video

import androidx.lifecycle.Lifecycle

interface VideoContract {
    interface View{
        fun getMLifecycle():Lifecycle
    }
}