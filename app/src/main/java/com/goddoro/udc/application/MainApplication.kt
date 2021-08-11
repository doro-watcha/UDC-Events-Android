package com.goddoro.udc.application

import android.app.Application
import android.content.Context
import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.common.common.NAVER_MAP_CLIENT_ID
import com.goddoro.common.di.apiModule
import com.goddoro.common.di.networkModule
import com.goddoro.common.di.repositoryModule
import com.goddoro.common.di.utilModule
import com.goddoro.common.util.AppPreference
import com.goddoro.udc.di.navigationModule
import com.goddoro.udc.di.viewModelModule
import com.goddoro.udc.views.auth.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK
import com.naver.maps.map.NaverMapSdk
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * created By DORO 2020/07/12
 */

class MainApplication : Application() {

    companion object {
        private lateinit var mainApp: MainApplication
        val context: Context by lazy {
            mainApp.applicationContext
        }

        lateinit var appPreference: AppPreference
    }

    override fun onCreate() {
        super.onCreate()

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)

        KakaoSDK.init(KakaoSDKAdapter(this))

        inject()

        appPreference = get()


    }

    private fun inject() {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.INFO)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    apiModule,
                    utilModule,

                    navigationModule
                )
            )
        }
    }

}