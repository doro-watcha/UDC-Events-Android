package com.goddoro.common.util

import android.app.Activity


/**
 * created By DORO 2020/10/10
 */

interface Navigator {


    fun startUploadEventActivity ( activity : Activity)

    fun startNeedLoginActivity ( activity : Activity)

    fun startMainActivity (activity : Activity , clearTask : Boolean  )

    fun startSettingActivity ( activity : Activity )

    fun startEventDetailActivity (activity : Activity, eventId : Int )

    fun startTagDetailActivity (activity : Activity, position :Int)

    fun startNotificationListActivity ( activity : Activity )

    fun startClassDetailActivity ( activity : Activity, classId : Int)
}