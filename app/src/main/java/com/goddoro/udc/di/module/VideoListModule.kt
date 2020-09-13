package com.goddoro.udc.di.module


/**
 * created By DORO 2020/08/16
 */

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.video.VideoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/08/16
 */

@Module
abstract class VideoListModule
{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(VideoListViewModel::class)
    abstract fun bindStatusViewModel(viewModel : VideoListViewModel) : ViewModel
}