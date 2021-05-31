package com.goddoro.common.di

import com.goddoro.common.util.*
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val utilModule = module {


    single { AppPreference(get()) }
    single { UriUtil(get()) }
    single { TokenUtil(get()) }
    single { MultiPartUtil(get())}
    single { ScreenUtil(get()) }
    single { DateUtil()}
    single { ToastUtil(get())}

}