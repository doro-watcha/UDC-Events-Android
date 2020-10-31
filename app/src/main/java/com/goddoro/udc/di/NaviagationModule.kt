package com.goddoro.udc.di

import com.goddoro.common.util.Navigator
import com.goddoro.udc.util.NavigatorImpl
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * created By DORO 2020/10/10
 */

val navigationModule = module {

    single { NavigatorImpl() } bind Navigator::class

}