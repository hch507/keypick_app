package com.keyword.keyword_miner.ui

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.keyword.keyword_miner.data.local.sharePref.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    companion object
    {
 //       lateinit var prefs: PreferenceUtil
        lateinit var instance: App
            private set
    }

    override fun onCreate()
    {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
    }

    fun getAppContext(): Context = instance.applicationContext
}