package com.goddoro.udc.di

import com.goddoro.map.EventMapViewModel
import com.goddoro.udc.MainViewModel
import com.goddoro.udc.views.classShop.ClassShopViewModel
import com.goddoro.udc.views.auth.AuthViewModel
import com.goddoro.udc.views.classShop.detail.ClassDetailViewModel
import com.goddoro.udc.views.event.detail.EventDetailViewModel
import com.goddoro.udc.views.upload.map.SearchAddressViewModel
import com.goddoro.udc.views.home.HomeViewModel
import com.goddoro.udc.views.notification.NotificationViewModel
import com.goddoro.udc.views.profile.EventCollectionViewModel
import com.goddoro.udc.views.profile.JoinEventViewModel
import com.goddoro.udc.views.profile.ProfileViewModel
import com.goddoro.udc.views.search.SearchViewModel
import com.goddoro.udc.views.search.detail.SearchDetailViewModel
import com.goddoro.udc.views.setting.SettingViewModel
import com.goddoro.udc.views.tag.TagDetailViewModel
import com.goddoro.udc.views.udc.UdcViewModel
import com.goddoro.udc.views.upload.UploadEventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val viewModelModule  = module {

    viewModel { AuthViewModel(get(),get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (eventId : Int ) -> EventDetailViewModel(eventId,get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { EventCollectionViewModel(get()) }
    viewModel { UploadEventViewModel(get())}
    viewModel { JoinEventViewModel() }
    viewModel{ (authorId : Int) -> ProfileViewModel(authorId, get(),get()) }
    viewModel { UdcViewModel() }
    viewModel { SettingViewModel(get()) }

    viewModel { EventMapViewModel(get(),get())}
    viewModel { TagDetailViewModel(get()) }

    viewModel { ClassShopViewModel(get()) }
    viewModel { NotificationViewModel(get()) }

    viewModel { SearchViewModel() }
    viewModel { ( query : String) -> SearchDetailViewModel(query,get()) }

    viewModel { (classId : Int ) -> ClassDetailViewModel(classId,get()) }
    viewModel { SearchAddressViewModel(get()) }
}