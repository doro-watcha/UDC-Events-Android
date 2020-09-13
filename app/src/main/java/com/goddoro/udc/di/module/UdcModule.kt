package com.goddoro.udc.di.module


/**
 * created By DORO 2020/08/16
 */

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.udc.UdcViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/16
 */

@Module
abstract class UdcModule
{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(UdcViewModel::class)
    abstract fun bindStatusViewModel(viewModel : UdcViewModel) : ViewModel
}