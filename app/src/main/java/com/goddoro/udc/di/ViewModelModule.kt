package com.goddoro.udc.di

import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.Genre
import com.goddoro.map.EventMapViewModel
import com.goddoro.udc.MainViewModel
import com.goddoro.udc.views.admin.AdminViewModel
import com.goddoro.udc.views.classShop.ClassShopViewModel
import com.goddoro.udc.views.auth.LoginViewModel
import com.goddoro.udc.views.classShop.detail.ClassDetailViewModel
import com.goddoro.udc.views.classShop.GenreClassViewModel
import com.goddoro.udc.views.event.detail.EventDetailViewModel
import com.goddoro.udc.views.upload.map.SearchAddressViewModel
import com.goddoro.udc.views.event.EventViewModel
import com.goddoro.udc.views.intro.IntroViewModel
import com.goddoro.udc.views.notification.NotificationViewModel
import com.goddoro.udc.views.profile.collection.EventCollectionViewModel
import com.goddoro.udc.views.profile.join.JoinEventViewModel
import com.goddoro.udc.views.profile.ProfileViewModel
import com.goddoro.udc.views.profile.pending.PendingEventViewModel
import com.goddoro.udc.views.search.SearchViewModel
import com.goddoro.udc.views.search.detail.SearchDetailViewModel
import com.goddoro.udc.views.setting.SettingViewModel
import com.goddoro.udc.views.tag.TagDetailViewModel
import com.goddoro.udc.views.udc.UdcViewModel
import com.goddoro.udc.views.upload.UploadEventViewModel
import com.goddoro.udc.views.upload.academy.AcademyPickViewModel
import com.goddoro.udc.views.upload.academy.UploadAcademyViewModel
import com.goddoro.udc.views.upload.danceClass.genre.GenrePickViewModel
import com.goddoro.udc.views.upload.danceClass.UploadClassViewModel
import com.goddoro.udc.views.upload.danceClass.schedule.SchedulePickViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val viewModelModule  = module {

    viewModel { LoginViewModel(get(),get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (event : Event) -> EventDetailViewModel(event,get()) }

    viewModel { EventViewModel(get(),get()) }
    viewModel { EventCollectionViewModel(get()) }
    viewModel { UploadEventViewModel(get())}
    viewModel { JoinEventViewModel() }
    viewModel{ (authorId : Int) -> ProfileViewModel(authorId, get(),get()) }
    viewModel { UdcViewModel() }
    viewModel { SettingViewModel(get(),get()) }

    viewModel { EventMapViewModel(get(),get())}
    viewModel { TagDetailViewModel(get()) }

    viewModel { ClassShopViewModel(get(),get()) }
    viewModel { NotificationViewModel(get()) }

    viewModel { SearchViewModel(get()) }
    viewModel { ( query : String) -> SearchDetailViewModel(query,get()) }

    viewModel { (danceClass : DanceClass ) -> ClassDetailViewModel(danceClass,get()) }
    viewModel { SearchAddressViewModel(get()) }

    viewModel { AdminViewModel(get()) }
    viewModel { IntroViewModel(get(),get()) }

    viewModel { PendingEventViewModel(get()) }

    viewModel { UploadClassViewModel(get())}
    viewModel { UploadAcademyViewModel(get(),get()) }
    viewModel { AcademyPickViewModel(get()) }

    viewModel { ( genre : Genre) -> GenreClassViewModel(genre, get()) }
    viewModel { GenrePickViewModel(get()) }
    viewModel { SchedulePickViewModel()}
}