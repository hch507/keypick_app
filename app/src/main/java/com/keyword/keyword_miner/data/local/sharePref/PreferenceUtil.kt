package com.keyword.keyword_miner.data.local.sharePref

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceUtil @Inject constructor(
    private var prefs: SharedPreferences)
{
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

}