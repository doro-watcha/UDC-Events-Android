package com.goddoro.common.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.goddoro.common.data.api.response.ApiResponse


/**
 * created By DORO 1/7/21
 */

interface DeviceAPI {


    @POST("v1/device")
    suspend fun registerDevice(
        @Body parameters: HashMap<String, Any>
    ) : ApiResponse<Any>

}