package com.goddoro.udc.navigator

import android.app.Activity
import android.content.Intent
import com.goddoro.common.navigator.Navigator
import com.goddoro.udc.views.upload.UploadEventActivity


/**
 * created By DORO 2020/09/13
 */

class NavigatorImpl : Navigator {
    override fun startUploadEventActivity(activity: Activity) {
        val intent = Intent ( activity, UploadEventActivity::class.java)
        activity.startActivity(intent)
    }


}