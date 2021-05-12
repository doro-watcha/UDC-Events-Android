package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.data.api.NotificationAPI
import com.goddoro.common.data.model.Notification
import com.goddoro.common.data.repository.NotificationRepository


/**
 * created By DORO 2/2/21
 */

class NotificationRepositoryImpl ( val api : NotificationAPI) : NotificationRepository {


    override suspend fun listNotifications(): List<Notification> {

        return api.listNotifications().unWrapData().notifications
    }


}