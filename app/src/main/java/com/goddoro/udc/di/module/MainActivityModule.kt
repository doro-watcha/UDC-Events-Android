package com.goddoro.udc.di.module

import androidx.lifecycle.ViewModel
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.MainViewModel
import com.goddoro.udc.di.ActivityScope
import com.goddoro.udc.di.FragmentScope
import com.goddoro.udc.di.ViewModelKey
import com.goddoro.udc.views.event.EventFragment
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.profile.EventCollectionFragment
import com.goddoro.udc.views.profile.JoinEventFragment
import com.goddoro.udc.views.profile.ProfileFragment
import com.goddoro.udc.views.udc.UdcFragment
import com.goddoro.udc.views.video.VideoListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
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


    @ContributesAndroidInjector(modules=[EventModule::class])
    @FragmentScope
    abstract fun contributesEventFragment() : EventFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    @FragmentScope
    abstract fun contributesHomeFragment() : HomeFragment

    @ContributesAndroidInjector(modules = [UdcModule::class])
    @FragmentScope
    abstract fun contributesUdcFragment() : UdcFragment

    @ContributesAndroidInjector(modules = [VideoListModule::class])
    @FragmentScope
    abstract fun contributesVideoListFragment() : VideoListFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    @FragmentScope
    abstract fun contributesProfileFragment() : ProfileFragment

    @ContributesAndroidInjector(modules = [CollectionModule::class])
    @FragmentScope
    abstract fun contributesCollectionFragment() : EventCollectionFragment

    @ContributesAndroidInjector(modules = [JoinEventModule::class])
    @FragmentScope
    abstract fun contributesJoinEventFragment() : JoinEventFragment

    @ContributesAndroidInjector(modules = [MapModule::class])
    @FragmentScope
    abstract fun contributesJoinEventMapFragment() : EventMapFragment

}