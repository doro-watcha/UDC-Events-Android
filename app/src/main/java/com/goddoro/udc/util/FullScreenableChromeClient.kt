package com.goddoro.udc.util

import android.R
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.core.content.ContextCompat


/**
 * created By DORO 2/15/21
 */

class FullScreenableChromeClient(activity: Activity?) : WebChromeClient() {
    private var mActivity: Activity? = null
    private var mCustomView: View? = null
    private var mCustomViewCallback: CustomViewCallback? = null
    private var mOriginalOrientation = 0
    private var mFullscreenContainer: FrameLayout? = null
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
    }

    override fun onShowCustomView(
        view: View?,
        callback: CustomViewCallback
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (mCustomView != null) {
                callback.onCustomViewHidden()
                return
            }
            mOriginalOrientation = mActivity!!.requestedOrientation
            val decor = mActivity!!.window.decorView as FrameLayout
            mFullscreenContainer = FullscreenHolder(mActivity)
            mFullscreenContainer?.addView(
                view,
                COVER_SCREEN_PARAMS
            )
            decor.addView(
                mFullscreenContainer,
                COVER_SCREEN_PARAMS
            )
            mCustomView = view
            setFullscreen(true)
            mCustomViewCallback = callback
            //          mActivity.setRequestedOrientation(requestedOrientation);
        }
        super.onShowCustomView(view, callback)
    }

    override fun onShowCustomView(
        view: View?,
        requestedOrientation: Int,
        callback: CustomViewCallback
    ) {
        this.onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
        if (mCustomView == null) {
            return
        }
        setFullscreen(false)
        val decor = mActivity!!.window.decorView as FrameLayout
        decor.removeView(mFullscreenContainer)
        mFullscreenContainer = null
        mCustomView = null
        mCustomViewCallback!!.onCustomViewHidden()
        mActivity?.requestedOrientation = mOriginalOrientation
    }

    private fun setFullscreen(enabled: Boolean) {
        val win = mActivity!!.window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_FULLSCREEN
        if (enabled) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
            if (mCustomView != null) {
                mCustomView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
        win.attributes = winParams
    }

    private class FullscreenHolder(ctx: Context?) : FrameLayout(ctx!!) {
        override fun onTouchEvent(evt: MotionEvent): Boolean {
            return true
        }

        init {
            setBackgroundColor(
                ContextCompat.getColor(
                    ctx!!,
                    R.color.black
                )
            )
        }
    }

    companion object {
        private val COVER_SCREEN_PARAMS = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    init {
        mActivity = activity
    }
}
