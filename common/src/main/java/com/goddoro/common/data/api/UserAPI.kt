package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.User
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


/**
 * created By DORO 2020/10/10
 */

interface UserAPI {


    /**
     * Get User
     */
    @GET("user/{id}")
    fun getUser(@Path("id") userId: Int): ApiResponse<UserGetResponse>

}

@Parcelize
data class UserGetResponse(
    @SerializedName("user")
    val user: User
) : Parcelable
