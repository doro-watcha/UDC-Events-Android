package com.goddoro.udc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.*
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


/**
 * created By DORO 2020/08/14
 */

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MapKey
annotation class ViewModelKey(val value : KClass<out ViewModel>)

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val creators : MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creators[modelClass]?.get() as T
    }
}