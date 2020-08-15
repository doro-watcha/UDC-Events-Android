package com.goddoro.udc.application

import android.app.Application
import com.goddoro.udc.di.*
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

    }

    companion object {
        lateinit var GlobalApp : MainApplication

    }
}