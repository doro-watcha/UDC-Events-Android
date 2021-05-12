package com.goddoro.udc.util

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Message
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


/**
 * created By DORO 2/15/21
 */

open class UdcWebClient : WebViewClient {

    private val TAG = UdcWebClient::class.java.simpleName

    private var mContext: Context? = null
    private var clearHistory = false
    private var isRedirected = false

    private var mLastUrl = ""

    constructor(context: Context) {
        mContext = context
    }

    constructor(context: Context, _lastUrl: String) {
        mContext = context
        mLastUrl = _lastUrl
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return handleUri(url, view)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return handleUri(request.url.toString(), view)
    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        isRedirected = false
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (!isRedirected) {
            if (clearHistory) {
                clearHistory = false
                view.clearCache(true)
                view.clearHistory()
            }
        }
    }

    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        LoadDialog.hideLoading()
    }

    private fun handleUri(url: String, view: WebView): Boolean {
        val INTENT_PROTOCOL_START1 = "intent:"
        val INTENT_PROTOCOL_START2 = "intent://"
        val INTENT_PROTOCOL_INTENT = "#Intent;"
        val INTENT_PROTOCOL_END = ";end;"
        val GOOGLE_PLAY_STORE_PREFIX = "market://details?id="
        val PKG_NAME_HEADER = "package="
        //        final String preYoutube = "www.youtube.com";

        if (url.startsWith("http://") || url.startsWith("https://")) {
            view.loadUrl(url)
            isRedirected =
                true // http://stackoverflow.com/questions/18282892/android-webview-onpagefinished-called-twice
            return false
        } else {
            if (url.startsWith(INTENT_PROTOCOL_START2)) {
                var pkgName: String? = ""
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    pkgName = intent.getPackage()
                    val existPackage = mContext!!.packageManager.getLaunchIntentForPackage(intent.getPackage()!!)
                    mContext!!.startActivity(intent)
                    return true
                } catch (e1: ActivityNotFoundException) {
                    val marketIntent = Intent(Intent.ACTION_VIEW)
                    marketIntent.data = Uri.parse("market://details?id=" + pkgName!!)
                    mContext!!.startActivity(marketIntent)
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return true
            } else if (url.startsWith(INTENT_PROTOCOL_START1)) {
                val customUrlStartIndex = INTENT_PROTOCOL_START1.length
                val customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT)
                if (customUrlEndIndex < 0) {
                    return false
                } else {
                    val customUrl = url.substring(customUrlStartIndex, customUrlEndIndex)
                    try {
                        mContext!!.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)))
                    } catch (e: ActivityNotFoundException) {
                        val packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length
                        val packageEndIndex = url.indexOf(INTENT_PROTOCOL_END)

                        val packageName =
                            url.substring(packageStartIndex, if (packageEndIndex < 0) url.length else packageEndIndex)
                        var pure_pkg_name = packageName
                        if (packageName.startsWith(PKG_NAME_HEADER)) {
                            pure_pkg_name = pure_pkg_name.substring(PKG_NAME_HEADER.length)
                        }
                        val marketLaunch = Intent(Intent.ACTION_VIEW)
                        marketLaunch.data = Uri.parse(GOOGLE_PLAY_STORE_PREFIX + pure_pkg_name)
                        marketLaunch.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        mContext!!.startActivity(marketLaunch)
                    }

                    return true
                }
            } else {
                return false
            }
        }
    }

    // http://action713.tistory.com/video/webview-%ED%8A%B9%EC%A0%95%EC%83%81%ED%99%A9%EC%97%90-%EB%92%A4%EB%A1%9C%EA%B0%80%EA%B8%B0%EB%B2%84%ED%8A%BC-%EB%A8%B9%ED%86%B5-%EA%B7%80%EC%B0%AE%EC%95%84%EC%A0%95%EB%A7%90
    // back key 구현시 평소에는 잘되는데 특정상황( 결제모듈, 페이스북접속)시 뒤로가기키가 먹통되는 현상이 있을때 아래 code로 해결
    override fun onFormResubmission(view: WebView, dontResend: Message, resend: Message) {
        resend.sendToTarget()
    }
}
