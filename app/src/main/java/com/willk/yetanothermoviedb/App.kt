package com.willk.yetanothermoviedb

import android.app.Application

class App : Application(){

    init {
        if(appInstance ==null)
        {
            appInstance =this
        }
    }

    companion object {
        private var appInstance: App? = null
        fun appInstance() : App? {
            return appInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        if(appInstance ==null)
        {
            appInstance =this
        }
    }
}