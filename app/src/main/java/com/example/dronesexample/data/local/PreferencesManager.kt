package com.example.dronesexample.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.dronesexample.R

class PreferencesManager(private val context: Context) {

    private val sp: SharedPreferences by lazy { initSp() }

    fun putString(key: String, value: String) = sp.edit().putString(key, value).apply()

    fun getString(key: String) = sp.getString(key, "false")

    private fun initSp(): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.shared_preferences_filename), Context.MODE_PRIVATE)
    }
}