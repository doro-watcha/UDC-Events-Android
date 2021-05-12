package com.goddoro.udc.views.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Notification
import com.goddoro.common.data.repository.NotificationRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/10/24
 */

class NotificationViewModel (
    private val notificationRepository: NotificationRepository
): ViewModel() {

    private val TAG = NotificationViewModel::class.java.simpleName



    val notifications : MutableLiveData<List<Notification>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {
        listNotifications()
    }

    private fun listNotifications () {

        viewModelScope.launch {
            kotlin.runCatching {
                notificationRepository.listNotifications()
            }.onSuccess {
                debugE(TAG, "NOTI RECEIVED")
                notifications.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }

        }
    }
}