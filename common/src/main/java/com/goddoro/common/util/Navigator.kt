package com.goddoro.common.util

import android.app.Activity
import android.widget.ImageView


/**
 * created By DORO 2020/10/10
 */

interface Navigator {


    fun startUploadEventActivity ( activity : Activity)

    fun startNeedLoginActivity ( activity : Activity)

    fun startMainActivity (activity : Activity , clearTask : Boolean  )

    fun startSettingActivity ( activity : Activity )

    fun startEventDetailActivity (activity : Activity, eventId : Int , imageView : ImageView )

    fun startTagDetailActivity (activity : Activity, position :Int)

    fun startNotificationListActivity ( activity : Activity )

    fun startClassDetailActivity ( activity : Activity, classId : Int)

    fun startSearchActivity ( activity : Activity )

    fun startSearchDetailActivity ( activity : Activity, query : String)

    fun startSearchAddressActivity ( activity : Activity)

    fun startAdminActivity ( activity : Activity)

    fun startMakeClassActivity ( activity : Activity )
}