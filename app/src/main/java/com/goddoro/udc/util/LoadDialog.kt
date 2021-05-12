package com.goddoro.udc.util

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import android.widget.ProgressBar
import com.goddoro.udc.R


/**
 * created By DORO 2/15/21
 */

object LoadDialog {
    private var m_loadingDialog: Dialog? = null
    fun showLoading(context: Context) {
        try {
            if (m_loadingDialog == null) {
                m_loadingDialog = Dialog(context, R.style.LoadDialog)
                val pb = ProgressBar(context)
                pb.indeterminateTintList = ColorStateList.valueOf(
                    context.resources.getColor(R.color.progress, null)
                )
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                m_loadingDialog!!.addContentView(pb, params)
                m_loadingDialog!!.setCancelable(false)
            }
            m_loadingDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        }
    }

    fun hideLoading() {
        try {
            if (m_loadingDialog != null && m_loadingDialog!!.isShowing) {
                m_loadingDialog!!.dismiss()
                m_loadingDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
