package com.goddoro.common.util

import android.app.Application


class ScreenUtil(private val context: Application) {
    val screenWidth: Int
        get() {
            return context.resources.displayMetrics.widthPixels
        }

    val screenHeight: Int
        get() = context.resources.displayMetrics.heightPixels

    fun toPixel(dp: Int): Float = context.resources.displayMetrics.density * dp
}