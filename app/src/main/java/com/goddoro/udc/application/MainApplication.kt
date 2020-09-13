package com.goddoro.udc.application

import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.udc.di.DaggerAppComponent
import com.naver.maps.map.NaverMapSdk
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/**
 * created By DORO 2020/07/12
 */

class MainApplication : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().app(this).build().also {  it.inject(this) }
    }
    override fun onCreate() {
        super.onCreate()

        GlobalApp = this

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_CLIENT_ID)





    }

    companion object {
        lateinit var GlobalApp : MainApplication

    }
}