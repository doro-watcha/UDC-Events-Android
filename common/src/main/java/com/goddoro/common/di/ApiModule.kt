package com.goddoro.common.di

import com.goddoro.common.data.api.AuthAPI
import com.goddoro.common.data.api.EventAPI
import com.goddoro.common.data.api.UserAPI
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * created By DORO 2020/10/10
 */

val apiModule = module {


    single { get<Retrofit>().create(AuthAPI::class.java) }
    single { get<Retrofit>().create(UserAPI::class.java)}
    single { get<Retrofit>().create(EventAPI::class.java)}

}