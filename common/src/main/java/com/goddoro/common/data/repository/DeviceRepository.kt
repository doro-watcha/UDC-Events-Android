package com.goddoro.common.data.repository

import io.reactivex.Completable


/**
 * created By DORO 1/7/21
 */

interface DeviceRepository {

    suspend fun registerDevice (
        fcmToken : String
    ) : Completable
}