package com.goddoro.udc.util

import android.app.Activity
import android.content.Intent
import com.goddoro.common.util.Navigator
import com.goddoro.common.views.NeedLoginActivity
import com.goddoro.udc.MainActivity
import com.goddoro.udc.views.auth.AuthActivity
import com.goddoro.udc.views.classShop.detail.ClassDetailActivity
import com.goddoro.udc.views.event.detail.EventDetailActivity
import com.goddoro.udc.views.notification.NotificationListActivity
import com.goddoro.udc.views.setting.SettingActivity
import com.goddoro.udc.views.tag.TagDetailActivity
import com.goddoro.udc.views.upload.UploadEventActivity


/**
 * created By DORO 2020/10/10
 */

class NavigatorImpl : Navigator{
    override fun startUploadEventActivity(activity: Activity) {
        val intent = Intent ( activity , UploadEventActivity::class.java)
        activity.startActivity(intent)
    }

    override fun startNeedLoginActivity(activity: Activity) {
        val intent = Intent ( activity , AuthActivity::class.java)
        activity.startActivity(intent)
    }

    override fun startMainActivity(activity: Activity, clearTask: Boolean) {
        val intent = Intent(activity, MainActivity::class.java)

        if (clearTask) {
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        activity.startActivity(intent)

//        pus(activity, intent)
    }

    override fun startSettingActivity (activity : Activity) {
        val intent = Intent ( activity, SettingActivity::class.java)
        activity.startActivity(intent)
    }

    override fun startEventDetailActivity(activity: Activity, eventId: Int) {
        val intent = EventDetailActivity.newIntent(activity,eventId)
        activity.startActivity(intent)
    }

    override fun startTagDetailActivity(activity: Activity, position: Int) {

        val intent = TagDetailActivity.newIntent(activity, position)
        activity.startActivity(intent)
    }

    override fun startNotificationListActivity(activity: Activity) {

        val intent = NotificationListActivity.newIntent(activity)
        activity.startActivity(intent)
    }

    override fun startClassDetailActivity(activity: Activity, classId : Int) {

        val intent = ClassDetailActivity.newIntent(activity,classId)
        activity.startActivity(intent)
    }
}