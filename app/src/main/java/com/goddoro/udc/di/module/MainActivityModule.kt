package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.MainViewModel
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/15
 */

@Module
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}