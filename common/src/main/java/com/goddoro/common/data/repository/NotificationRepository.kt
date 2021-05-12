package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Notification


/**
 * created By DORO 2/2/21
 */

interface NotificationRepository {


    suspend fun listNotifications (

    ) : List<Notification>

}