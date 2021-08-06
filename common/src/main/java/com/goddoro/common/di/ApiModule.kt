package com.goddoro.common.di

import com.goddoro.common.data.api.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * created By DORO 2020/10/10
 */

val apiModule = module {


    single { get<Retrofit>().create(AuthAPI::class.java) }
    single { get<Retrofit>().create(UserAPI::class.java)}
    single { get<Retrofit>().create(EventAPI::class.java)}
    single { get<Retrofit>().create(SearchAPI::class.java)}
    single { get<Retrofit>().create(ClassAPI::class.java)}
    single { get<Retrofit>().create(DeviceAPI::class.java)}
    single { get<Retrofit>().create(AcademyAPI::class.java)}
    single { get<Retrofit>().create(NotificationAPI::class.java)}
    single { get<Retrofit>().create(GenreAPI::class.java)}
    single { get<Retrofit>().create(VersionAPI::class.java)}
    single { get<Retrofit>(named("NAVER")).create(NaverAPI::class.java)}
    single { get<Retrofit>(named("NAVER_OPEN")).create(NaverOpenAPI::class.java)}

}