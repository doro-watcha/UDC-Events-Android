package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.DeviceAPI
import com.goddoro.common.data.repository.DeviceRepository
import io.reactivex.Completable


/**
 * created By DORO 1/7/21
 */

class DeviceRepositoryImpl ( val api : DeviceAPI) : DeviceRepository {
    override suspend fun registerDevice(fcmToken: String): Completable {

        val params = hashMapOf(
            "token" to fcmToken
        ).filterValueNotNull()
        return api.registerDevice(params).unWrapCompletable()
    }


}