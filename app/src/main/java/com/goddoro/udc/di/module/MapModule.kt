package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.map.EventMapViewModel
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/09/12
 */

@Module
abstract class MapModule
{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(EventMapViewModel::class)
    abstract fun bindStatusViewModel(viewModel : EventMapViewModel) : ViewModel



}