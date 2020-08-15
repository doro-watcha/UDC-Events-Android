package com.goddoro.common.data.service

import android.annotation.SuppressLint
import android.os.Parcelable
import com.goddoro.common.data.data.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*


/**
 * created By DORO 2020/08/16
 */

interface UserService {

    //1
    @POST("auth/signup")
    @FormUrlEncoded
    suspend fun signUp(
        @Header("access_token") facebookAccessToken : String,
        @Field("nickname") nickname: String,
        @Field("pay_password") pay_password: String
    ):TokenResponse

    //2
    @GET("auth/signin")
    suspend fun signIn(
        @Header("access_token") facebookAccessToken : String,
        @Header("firebase_token") firebaseFCMToken:String
    ):TokenResponse

    //3
    @GET("auth/user")
    suspend fun getUser(
    ):UserResponse

    @FormUrlEncoded
    @POST("v2/auth/signin")
    suspend fun signin (
        @Field("email") email : String?,
        @Field("password") password : String?
    ) : AuthSignInResponse

    //5
    @PUT("mypage/point")
    @FormUrlEncoded
    suspend fun chargeFunditoMoney(
        @Field("funditoMoney") fundingMoney: Int,
        @Field("payPassword") payPassword: String
    )

    //6
    @GET("mypage/fund/reward")
    suspend fun getUsingFunditoMoney(): CurrentFundingStatus

    //7

    @GET("mypage/point")
    suspend fun getFunditoMoney(): List<FunditoMoneyResponse>

    @PUT("mypage/point/withdraw")
    @FormUrlEncoded
    suspend fun withdrawFunditoMoney(
        @Field("storeIdx") storeIdx:Int,
        @Field("rewardMoney") rewardMoney: Int
    )

    @POST("auth/signup/nickname")
    @FormUrlEncoded
    suspend fun checkDuplicateNickname(
        @Field("nickname")nickName: String
    )


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

@SuppressLint("ParcelCreator")
@Parcelize
data class CurrentFundingStatus(
    @SerializedName("totalGetMoney")
    @Expose(serialize = true, deserialize = true)
    val totalGetMoney: Int,
    @SerializedName("totalFundedMoney")
    @Expose(serialize = true, deserialize = true)
    val totalFundedMoney: Int,
    @SerializedName("totalRewardMoney")
    @Expose(serialize = true, deserialize = true)
    val totalRewardMoney: Int,
    @SerializedName("totalRewardPercent")
    @Expose(serialize = true, deserialize = true)
    val totalRewardPercent: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class FunditoMoneyResponse(
    @SerializedName("point")
    @Expose(serialize = true, deserialize = true)
    val point: Int
) : Parcelable

@Parcelize
data class AuthSignInResponse(
    @SerializedName("user")
    val user: User?,

    @SerializedName("token")
    val token: String?
) : Parcelable
