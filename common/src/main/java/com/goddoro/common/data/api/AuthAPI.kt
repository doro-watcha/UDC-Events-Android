package com.goddoro.common.data.api

import android.annotation.SuppressLint
import android.os.Parcelable
import com.goddoro.common.data.api.response.ApiResponse
import com.goddoro.common.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * created By DORO 2020/10/03
 */


interface AuthAPI {

    /**
     * Email Sign Up
     */
    @POST("auth/signup")
    @FormUrlEncoded
    suspend fun signUp(
        @Field("loginId") loginId: String,
        @Field("password") password: String,
        @Field("username") username : String
    ): ApiResponse<AuthSignUpResponse>

    /**
     * Email Login
     */
    @POST("auth/signin")
    @FormUrlEncoded
    suspend fun signIn(
        @Field("loginId") loginId : String,
        @Field("password") password : String
    ) : ApiResponse<AuthSignInResponse>

    @POST("auth/social-signin")
    @FormUrlEncoded
    suspend fun snsSignUp(
        @FieldMap parameters : HashMap<String,Any>
    ) : ApiResponse<AuthSignUpResponse>

//    /**
//     * Token validate
//     */
//    @GET("auth/user")
//    suspend fun getUser(
//    ):UserResponse



}

@SuppressLint("ParcelCreator")
@Parcelize
data class TokenResponse(
    @SerializedName("token")
    @Expose(serialize = true, deserialize = true)
    val token: String
) : Parcelable

@Parcelize
data class UserResponse(
    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    val name: String,
    @SerializedName("nickname")
    @Expose(serialize = true, deserialize = true)
    val nickname: String,
    @SerializedName("point")
    @Expose(serialize = true, deserialize = true)
    val point: Int
) : Parcelable

@Parcelize
data class AuthSignInResponse(
    @SerializedName("user")
    val user: User,

    @SerializedName("token")
    val token: String
) : Parcelable

@Parcelize
data class AuthSignUpResponse(
    @SerializedName("user")
    val user : User,

    @SerializedName("token")
    val token : String
) : Parcelable
