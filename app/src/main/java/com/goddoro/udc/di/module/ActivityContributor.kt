package com.goddoro.udc.di.module

import com.goddoro.udc.MainActivity
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.views.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * created By DORO 2020/08/15
 */
@Module
abstract class ActivityContributor {


    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    @ActivityScope
    abstract fun contributesLoginActivity(): LoginActivity
}