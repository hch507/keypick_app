package com.keyword.keyword_miner.sharePref

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context:Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("Login", 0)

    fun getboolean(key: String, defValue: Boolean): Boolean
    {
        return prefs.getBoolean(key, defValue)
    }
    fun setboolean(key: String, defValue: Boolean)
    {
        return prefs.edit().putBoolean(key, defValue).apply()
    }
    fun getEmail(key: String, defValue: String): String
    {
        return prefs.getString(key, defValue).toString()
    }

    fun setEmail(key: String, str: String)
    {
        prefs.edit().putString(key, str).apply()
    }
    fun getName(key: String, defValue: String): String
    {
        return prefs.getString(key, defValue).toString()
    }

    fun setName(key: String, str: String)
    {
        prefs.edit().putString(key, str).apply()
    }
}