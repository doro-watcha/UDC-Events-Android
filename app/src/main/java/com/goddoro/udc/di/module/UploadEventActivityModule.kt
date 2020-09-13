package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.upload.UploadEventViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * created By DORO 2020/09/13
 */

@Module
abstract class UploadEventActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(UploadEventViewModel::class)
    abstract fun bindViewModel(viewModel : UploadEventViewModel) : ViewModel

}

