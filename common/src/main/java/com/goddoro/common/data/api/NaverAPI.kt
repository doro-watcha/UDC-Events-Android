package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap
import com.goddoro.common.data.api.response.ApiResponse

/**
 * created By DORO 4/25/21
 */

interface NaverAPI {


    @GET("/map-reversegeocode/v2/gc")
    suspend fun getAddress(
        @QueryMap parameters: HashMap<String, Any>
    ) : AddressResponse

    @GET("/map-geocode/v2/geocode")
    suspend fun getLocation(
        @QueryMap parameters : HashMap<String,Any>
    ) : LocationResponse


}


@Parcelize
data class AddressResponse(
    @SerializedName("status")
    val status : Status,

    @SerializedName("results")
    val results : List<Result>
) : Parcelable

@Parcelize
data class LocationResponse(

    @SerializedName("status")
    val status : String,

    @SerializedName("meta")
    val meta : Meta,

    @SerializedName("addresses")
    val addresses : List<Address>,

    @SerializedName("errorMessage")
    val errorMessage : String

) : Parcelable
