package com.goddoro.udc.views.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.EventRepository
import kotlinx.coroutines.launch

class IntroViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onErrorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {

        onNetworkCheck()
    }
    fun onNetworkCheck() {

        viewModelScope.launch {
            kotlin.runCatching {
                eventRepository.listEventsBySort("main")
            }.onSuccess {
                onLoadCompleted.value = Once(Unit)
            }.onFailure {
                onErrorInvoked.value = it
            }
        }



    }
}