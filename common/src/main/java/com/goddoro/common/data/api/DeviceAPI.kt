package com.goddoro.common.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


/**
 * created By DORO 1/7/21
 */

interface DeviceAPI {


    @POST("device")
    suspend fun registerDevice(
        @Body parameters: HashMap<String, Any>
    ) : ApiResponse<Any>

}