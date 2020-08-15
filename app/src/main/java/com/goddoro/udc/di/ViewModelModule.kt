package com.goddoro.udc.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.MainActivity
import com.goddoro.udc.MainViewModel
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
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