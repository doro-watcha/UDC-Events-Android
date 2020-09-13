package com.goddoro.common.mock

import android.content.Context
import android.os.Parcelable
import com.goddoro.common.data.service.AuthService
import com.goddoro.common.data.service.UserService
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * created By DORO 2020/09/13
 */


private const val FAKE_BASE_URL = "http://api.staging.onesongtwoshows.com"
private const val FAKE_JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MzAsImVtYWlsIjoicmljb2xhMjBAbmF2ZXIuY29tIiwiaWF0IjoxNTk5NzMwOTc1LCJleHAiOjE2MDQ5MTQ5NzV9.fYyaFrWxHG6PZionozWTJe2Dseu0xky01R-uRuwinXs"
object fakeNetworkModule {

    private const val BASE_URL = "http://ec2-3-35-4-201.ap-northeast-2.compute.amazonaws.com:3000/"

    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val commonNetworkInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            /**
             * 1) Common Header with API Access Token
             */
            val newRequest = chain.request().newBuilder()
                .addHeader("token", FAKE_JWT_TOKEN).build()

            /**
             * 2) General Response from Server (Unwrapping data)
             */
            val response = chain.proceed(newRequest)

            /**
             * 3) Parse body to json
             */
            val rawJson = response.body?.string() ?: "{}"


            /**
             * 4) Wrap body with gson
             */
            val type = object : TypeToken<ResponseWrapper<*>>() {}.type
            val res = try {
                val r = gson.fromJson<ResponseWrapper<*>>(rawJson, type) ?: throw JsonSyntaxException("Parse Fail")

                if(!r.isSuccess)
                    ResponseWrapper<Any>(
                        -999,
                        false,
                        "Server Logic Fail : ${r.message}",
                        null
                    )
                else
                    r

            } catch (e: JsonSyntaxException) {
                ResponseWrapper<Any>(
                    -999,
                    false,
                    "json parsing fail : $e",
                    null
                )
            } catch (t: Throwable) {
                ResponseWrapper<Any>(
                    -999,
                    false,
                    "unknown error : $t",
                    null
                )
            }


            /**
             * 5) get data json from data
             */
            val dataJson = gson.toJson(res.data)

            /**
             * 6) return unwrapped response with body
             */
            return response.newBuilder()
                .message(res.message ?: "")
                .body(dataJson.toResponseBody())
                .build()
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(commonNetworkInterceptor)
        .build()

    private val gson = GsonBuilder()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Services
     */

    val userService : UserService = retrofit.create(UserService::class.java)
    val authService : AuthService = retrofit.create(AuthService::class.java)

}

/**
 * For Response Form Unwrapping
 */
@Parcelize
data class ResponseWrapper<T>(
    @SerializedName("ecode")
    val ecode: Int,
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: @RawValue T? = null
) : Parcelable

