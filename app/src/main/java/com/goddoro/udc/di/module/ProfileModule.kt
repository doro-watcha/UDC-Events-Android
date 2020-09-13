package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/09/12
 */

@Module
abstract class ProfileModule
{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindStatusViewModel(viewModel : ProfileViewModel) : ViewModel



}