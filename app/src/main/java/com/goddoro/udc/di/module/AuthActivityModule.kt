package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.auth.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/15
 */

@Module
abstract class AuthActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindViewModel(viewModel : AuthViewModel) : ViewModel

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesLoginFragment() : LoginFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesSignUpFragment() : SignUpFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesFindEmailFragment() : EmailFindFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesFindPasswordFragment() : PasswordFindFragment
}