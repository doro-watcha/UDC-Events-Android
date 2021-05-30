package com.goddoro.udc.util

import android.app.Activity
import android.content.Intent
import android.util.EventLog
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Event
import com.goddoro.common.util.Navigator
import com.goddoro.udc.MainActivity
import com.goddoro.udc.R
import com.goddoro.udc.views.admin.AdminActivity
import com.goddoro.udc.views.auth.AuthActivity
import com.goddoro.udc.views.classShop.create.MakeClassActivity
import com.goddoro.udc.views.classShop.detail.ClassDetailActivity
import com.goddoro.udc.views.event.detail.EventDetailActivity
import com.goddoro.udc.views.upload.map.SearchAddressActivity
import com.goddoro.udc.views.notification.NotificationListActivity
import com.goddoro.udc.views.search.SearchActivity
import com.goddoro.udc.views.search.detail.SearchDetailActivity
import com.goddoro.udc.views.setting.SettingActivity
import com.goddoro.udc.views.tag.TagDetailActivity
import com.goddoro.udc.views.upload.UploadEventActivity


/**
 * created By DORO 2020/10/10
 */

class NavigatorImpl : Navigator{

    private fun fadeIn(activity: Activity, intent: Intent) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun slide( activity : Activity , intent : Intent ) {
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
    }
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

    override fun startEventDetailActivity(activity: Activity, event: Event, imageView : ImageView) {
        val intent = EventDetailActivity.newIntent(activity,event)
        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imageView,activity.getString(R.string.shared_element_transition)).toBundle())
    }

    override fun startTagDetailActivity(activity: Activity, position: Int) {

        val intent = TagDetailActivity.newIntent(activity, position)
        activity.startActivity(intent)
    }

    override fun startNotificationListActivity(activity: Activity) {

        val intent = NotificationListActivity.newIntent(activity)
        activity.startActivity(intent)
    }

    override fun startClassDetailActivity(activity: Activity, danceClass : DanceClass) {

        val intent = ClassDetailActivity.newIntent(activity,danceClass)
        activity.startActivity(intent)
    }

    override fun startSearchActivity(activity: Activity) {

        val intent = SearchActivity.newIntent(activity)
        fadeIn(activity,intent)
    }

    override fun startSearchDetailActivity(activity: Activity ,query : String ) {
        val intent = SearchDetailActivity.newIntent(activity,query)
        slide(activity,intent)
    }

    override fun startSearchAddressActivity(activity: Activity) {

        val intent = SearchAddressActivity.newIntent(activity)
        slide(activity,intent)
    }

    override fun startAdminActivity(activity: Activity) {
        val intent = AdminActivity.newIntent(activity)
        slide(activity,intent)
    }

    override fun startMakeClassActivity(activity: Activity) {
        val intent = MakeClassActivity.newIntent(activity)
        activity.startActivity(intent)
    }
}