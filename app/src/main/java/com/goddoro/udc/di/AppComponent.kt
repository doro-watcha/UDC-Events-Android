package com.goddoro.udc.di

import android.content.Context
import com.goddoro.udc.application.MainApplication
import com.goddoro.udc.di.module.ActivityContributor
import com.goddoro.udc.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Inject
import javax.inject.Scope


/**
 * created By DORO 2020/08/10
 */

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityContributor::class,
    ViewModelModule::class
])
@AppScope
interface AppComponent : AndroidInjector<MainApplication> {
    override fun inject(instance: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Context) : Builder

        fun build() :AppComponent
    }
}
