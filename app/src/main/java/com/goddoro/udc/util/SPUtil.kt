package com.goddoro.udc.util

import android.content.Context
import com.goddoro.udc.application.MainApplication.Companion.GlobalApp


/**
 * created By DORO 2020/08/16
 */

object SPUtil {

    private val packageName = GlobalApp.packageName

    private val preferences = GlobalApp.getSharedPreferences(packageName, Context.MODE_PRIVATE)


    var accessToken : String
        get() = preferences.getString("accessToken","") ?: ""
        set(value) = preferences.edit().putString("accessToken",value).apply()

}