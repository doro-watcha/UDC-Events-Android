package com.goddoro.common.di

import android.annotation.SuppressLint
import android.util.Log
import com.goddoro.common.util.AppPreference
import com.goddoro.common.util.TokenUtil
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * created By DORO 2020/10/10
 */

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

enum class ServerType(val apiUrl: String, val homepageUrl: String, val value: Int) {

    PRODUCTION("http://ec2-3-35-4-201.ap-northeast-2.compute.amazonaws.com:3000", "https://www.beatflo.co/", 0),
    DEVELOPMENT("https://api.staging.onesongtwoshows.com", "https://www.beatflo.co/", 1)
    ;

    companion object {

        fun parse(value: Int) = values().firstOrNull { it.value == value } ?: PRODUCTION

        val defaultServerType: ServerType
            get() = PRODUCTION

        fun getCurrentServer(appPreference: AppPreference): ServerType {
            return parse(appPreference.curServer)
        }
    }
}

@SuppressLint("ConstantLocale")
val networkModule = module {
    single { GsonBuilder().create() }

    single {
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(get<Interceptor>())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ServerType.getCurrentServer(get()).apiUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }
    single(named("UPLOAD")) {
        Retrofit.Builder()
            .baseUrl(ServerType.getCurrentServer(get()).apiUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single {
        Interceptor { chain ->
            val jwtToken = "JWT ${get<TokenUtil>().loadToken()}"
            val curLocale = Locale.getDefault().toString()
            Log.d(
                "Network Module",
                "========== [ Network Module : Header Intercepter ] =========== token = $jwtToken"
            )
            chain.proceed(chain.request().newBuilder().apply {
                header("Authorization", jwtToken)
                addHeader("Accept-Language", curLocale)
            }.build())
        }
    }
}