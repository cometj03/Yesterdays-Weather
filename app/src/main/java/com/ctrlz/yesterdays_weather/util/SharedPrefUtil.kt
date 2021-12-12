package com.ctrlz.yesterdays_weather.util

import android.content.Context
import android.content.SharedPreferences

private fun getPref(context: Context):
        SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

private fun getEditor(context: Context):
        SharedPreferences.Editor = getPref(context).edit()

fun put(context: Context, key: String, value: Any) {
    val editor = getEditor(context)
    with (editor) {
        when (value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            else -> return
        }
        apply()
    }
}

fun getInt(context: Context, key: String):
        Int = getPref(context).getInt(key, 0)

fun getBoolean(context: Context, key: String):
        Boolean = getPref(context).getBoolean(key, false)

fun getString(context: Context, key: String):
        String? = getPref(context).getString(key, "")