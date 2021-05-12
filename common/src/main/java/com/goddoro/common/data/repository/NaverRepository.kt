package com.goddoro.common.data.repository

import com.goddoro.common.data.api.AddressResponse
import com.goddoro.common.data.api.LocationResponse


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
}