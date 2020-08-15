package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/15
 */

@Module
abstract class LoginActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewModel : LoginViewModel) : ViewModel
}