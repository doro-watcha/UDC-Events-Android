package com.goddoro.udc.views.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.data.repository.UserRepository
import kotlinx.coroutines.launch

class IntroViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val onLoadCompleted: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onErrorInvoked: MutableLiveData<Throwable> = MutableLiveData()

    init {

        onNetworkCheck()
    }

    private fun onNetworkCheck() {

        if (authRepository.isSignedIn()) {

            viewModelScope.launch {
                kotlin.runCatching {
                    userRepository.getUser(authRepository.curUser.value?.id ?: 0)
                }.onSuccess {
                    authRepository.setCurrentUser(it)
                    onLoadCompleted.value = Once(Unit)
                }.onFailure {
                    onErrorInvoked.value = it
                }
            }
        } else {
            onLoadCompleted.value = Once(Unit)
        }


    }
}