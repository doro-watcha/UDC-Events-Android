package com.goddoro.common.di

import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.data.repository.UserRepository
import com.goddoro.common.data.repositoryImpl.AuthRepositoryImpl
import com.goddoro.common.data.repositoryImpl.EventRepositoryImpl
import com.goddoro.common.data.repositoryImpl.UserRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val repositoryModule = module {


    single { AuthRepositoryImpl(get(), get(), get(),get()) } bind AuthRepository::class
    single { UserRepositoryImpl(get()) } bind UserRepository::class
    single { EventRepositoryImpl(get()) } bind EventRepository::class

}