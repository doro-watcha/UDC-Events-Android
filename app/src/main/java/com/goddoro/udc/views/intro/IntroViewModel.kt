package com.goddoro.udc.views.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.data.repository.UserRepository
import com.goddoro.common.data.repository.VersionRepository
import kotlinx.coroutines.launch

class IntroViewModel(
    private val authRepository: AuthRepository,
    private val versionRepository: VersionRepository
) : ViewModel() {

    val onLoadCompleted: MutableLiveData<Once<Int>> = MutableLiveData()
    val onErrorInvoked: MutableLiveData<Throwable> = MutableLiveData()

    init {

        onVersionCheck()
    }

    private fun onVersionCheck() {

        viewModelScope.launch {

            kotlin.runCatching {
                versionRepository.getMinimumVersion()
            }.onSuccess {
                onLoadCompleted.value = Once(it)
            }.onFailure {
                onErrorInvoked.value = it
            }
        }
    }
}