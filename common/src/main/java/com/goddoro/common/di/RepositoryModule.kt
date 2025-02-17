package com.goddoro.common.di

import com.goddoro.common.data.repository.*
import com.goddoro.common.data.repositoryImpl.*
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val repositoryModule = module {


    single { AuthRepositoryImpl(get(), get(), get(),get(),get()) } bind AuthRepository::class
    single { UserRepositoryImpl(get(),get()) } bind UserRepository::class
    single { EventRepositoryImpl(get(),get()) } bind EventRepository::class
    single { SearchRepositoryImpl(get()) } bind SearchRepository::class
    single { ClassRepositoryImpl(get(),get()) } bind ClassRepository::class
    single { DeviceRepositoryImpl(get())} bind DeviceRepository::class
    single { NotificationRepositoryImpl(get())} bind NotificationRepository::class
    single { VersionRepositoryImpl(get())} bind VersionRepository::class
    single { NaverRepositoryImpl(get(),get())} bind NaverRepository::class
    single { AcademyRepositoryImpl(get(),get())} bind AcademyRepository::class
    single { GenreRepositoryImpl(get())} bind GenreRepository::class
    single { StarRepositoryImpl(get())} bind StarRepository::class

}