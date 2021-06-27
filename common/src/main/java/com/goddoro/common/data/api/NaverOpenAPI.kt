package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Result
import com.goddoro.common.data.model.Status
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NaverOpenAPI {

    @GET("/vi/nid/me")
    suspend fun getUserData(
        @Header("Authorization") authorization : String
    ) : NaverUser
}

@Parcelize
data class NaverUserResponse(
    @SerializedName("resultcode")
    val resultCode : Status,

    @SerializedName("message")
    val message : String,

    @SerializedName("response")
    val response : NaverUser
) : Parcelable

@Parcelize
data class NaverUser(
    @SerializedName("id")
    val id : String ,

    @SerializedName("profile_image")
    val profileImgUrl : String?,

    @SerializedName("email")
    val email : String?,

    @SerializedName("name")
    val name : String?
) : Parcelable
