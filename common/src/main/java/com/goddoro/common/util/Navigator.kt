package com.goddoro.common.util

import android.app.Activity
import android.util.EventLog
import android.widget.ImageView
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.Location


/**
 * created By DORO 2020/10/10
 */

interface Navigator {


    fun startUploadEventActivity ( activity : Activity)

    fun startLoginActivity ( activity : Activity)

    fun startMainActivity (activity : Activity , clearTask : Boolean  )

    fun startSettingActivity ( activity : Activity )

    fun startEventDetailActivity (activity : Activity, event : Event, imageView : ImageView )

    fun startTagDetailActivity (activity : Activity, position :Int)

    fun startNotificationListActivity ( activity : Activity )

    fun startClassDetailActivity ( activity : Activity, danceClass : DanceClass, imageView :ImageView)

    fun startSearchActivity ( activity : Activity )

    fun startSearchDetailActivity ( activity : Activity, query : String)

    fun startSearchAddressActivity ( activity : Activity, location : Location )

    fun startAdminActivity ( activity : Activity)

    fun startMakeClassActivity ( activity : Activity )
}