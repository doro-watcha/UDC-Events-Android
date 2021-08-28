package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.User
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.parcelize.Parcelize
import com.goddoro.common.data.api.response.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * created By DORO 2020/10/10
 */

interface UserAPI {


    /**
     * Get User
     */
    @GET("v1/user/{id}")
    suspend fun getUser(@Path("id") userId: Int): ApiResponse<UserGetResponse>

    /**
     * Update User
     */
    @PATCH("v1/user")
    @FormUrlEncoded
    suspend fun updateUser(
        @FieldMap parameters : HashMap<String,Any>
    ) : ApiResponse<Any>

    @PATCH("v1/user/profile")
    @Multipart
    suspend fun updateProfile(
        @Part files : List<MultipartBody.Part>?
    ) : ApiResponse<Any>

}

@Parcelize
data class UserGetResponse(
    @SerializedName("user")
    val user: User
) : Parcelable
