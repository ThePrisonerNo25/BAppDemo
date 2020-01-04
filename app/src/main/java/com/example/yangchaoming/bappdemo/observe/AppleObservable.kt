package com.example.yangchaoming.bappdemo.observe

import java.util.*

class AppleObservable private constructor():Observable(){
    companion object {
        private var instance: AppleObservable? = null
            get() {
                if (field == null) {
                    field = AppleObservable()
                }
                return field
            }
        @Synchronized
        fun get(): AppleObservable{
            return instance!!
        }
    }

    fun setMsg(obj:Any?){
        setChanged()
        notifyObservers(obj)
    }


}