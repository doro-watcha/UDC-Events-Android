package com.goddoro.common.data.repository

import com.goddoro.common.data.api.AddressResponse
import com.goddoro.common.data.api.LocationResponse
import com.goddoro.common.data.api.NaverUser
import com.goddoro.common.data.api.NaverUserResponse


/**
 * created By DORO 4/25/21
 */

interface NaverRepository {


    suspend fun getAddressFromLocation (
        latitude : Double,
        longitude : Double
    ) : AddressResponse


    suspend fun getLocationFromAddress(
        address : String
    ) : LocationResponse

    suspend fun getNaverUserData(
        accessToken : String
    ) : NaverUser
}