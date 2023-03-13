package com.example.keyword_miner.sharePref

import android.app.Application
import android.content.Context

class App:Application() {
    companion object
    {
        lateinit var prefs: PreferenceUtil
        lateinit var instance: App
            private set
    }

    override fun onCreate()
    {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
        instance = this
    }

    fun getAppContext(): Context = instance.applicationContext
}