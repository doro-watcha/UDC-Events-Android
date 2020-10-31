package com.goddoro.common.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.google.android.material.tabs.TabLayout
import java.io.*


/**
 * created By DORO 2020/10/24
 */

object CommonUtils {


    // Tab indicator 좌/우 margin 조정
    fun reduceMarginsInTabs(tabLayout: TabLayout, marginOffset: Int) {

        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                if (tabView.layoutParams is ViewGroup.MarginLayoutParams) {
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = marginOffset
                    (tabView.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = marginOffset
                }
            }

            tabLayout.requestLayout()
        }
    }


    /*
     * SD card directory 생상
     */
    const val BEATFLO_DIR = "BEATFLO"

    private fun makeBeatfloDir(context: Context) {
        val dir = File(context.getExternalFilesDir(null)?.absolutePath + File.separator + BEATFLO_DIR)
        if (!dir.exists() || !dir.isDirectory) {
            dir.mkdirs()
        }
    }

    private fun getBeatfloPath(context: Context): String {
        var beatflo_path = ""
        beatflo_path = (context.getExternalFilesDir(null)?.getAbsolutePath() +
                File.separator +
                BEATFLO_DIR)

        return beatflo_path
    }

    private fun requireSDKInt(sdkInt: Int): Boolean {
        return Build.VERSION.SDK_INT >= sdkInt
    }



}