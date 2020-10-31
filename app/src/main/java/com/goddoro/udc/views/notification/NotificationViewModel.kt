package com.goddoro.udc.views.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Notification


/**
 * created By DORO 2020/10/24
 */

class NotificationViewModel : ViewModel() {



    val notifications : MutableLiveData<List<Notification>> = MutableLiveData()


    init {

        notifications.value = listOf(
            Notification(0),Notification(0),
            Notification(0),Notification(0),
            Notification(0),Notification(0),
            Notification(0),Notification(0),
            Notification(0),Notification(0),
            Notification(0),Notification(0)
        )
    }
}