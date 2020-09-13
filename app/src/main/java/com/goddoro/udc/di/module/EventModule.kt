package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.event.EventViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/16
 */

@Module
abstract class EventModule
{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(EventViewModel::class)
    abstract fun bindStatusViewModel(viewModel : EventViewModel) : ViewModel
}