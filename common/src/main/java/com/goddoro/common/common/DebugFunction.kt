package com.goddoro.common.common

import android.util.Log
import com.goddoro.common.BuildConfig


/**
 * created By DORO 2020/09/12
 */

fun debugE(tag: String, message: Any?) {
    if (BuildConfig.DEBUG)
        Log.e(tag, "🧩" + message.toString() + "🧩")
}

fun debugE(message: Any?) {
    debugE("DEBUG", message)
}