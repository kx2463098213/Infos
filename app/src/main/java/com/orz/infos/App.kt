package com.orz.infos

import android.app.Application
import android.content.Context

/**
 * Created by Administrator on 2017/10/20.
 */
class App: Application(){

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }
}