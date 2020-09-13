package com.goddoro.udc.di.module

import com.goddoro.udc.MainActivity
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.views.auth.AuthActivity
import com.goddoro.udc.views.upload.UploadEventActivity
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

    @ContributesAndroidInjector(modules = [AuthActivityModule::class])
    @ActivityScope
    abstract fun contributesLoginActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [UploadEventActivityModule::class])
    @ActivityScope
    abstract fun contributesUploadEventActivity(): UploadEventActivity


}