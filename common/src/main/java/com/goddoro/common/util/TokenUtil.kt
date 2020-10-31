package com.goddoro.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


/**
 * created By DORO 2020/10/10
 */

class TokenUtil(context: Context) {

    companion object {
        private val TAG = TokenUtil::class.java.simpleName

        private const val KEY_TOKEN = "KEY_TOKEN"
    }

    private val preference: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @SuppressLint("ApplySharedPref")
    fun saveToken(token: String) {
        preference.edit().putString(KEY_TOKEN, token).commit()
    }

    fun loadToken(): String? = preference.getString(KEY_TOKEN, null)

    @SuppressLint("ApplySharedPref")
    fun clearToken() {
        preference.edit().remove(KEY_TOKEN).commit()
    }
}