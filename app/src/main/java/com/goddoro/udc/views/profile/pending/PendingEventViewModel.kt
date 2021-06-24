package com.goddoro.udc.views.profile.pending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import kotlinx.coroutines.launch

class PendingEventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    val pendingEvents : MutableLiveData<List<Event>> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()


    init {

        listPendingEvent()
    }
    private fun listPendingEvent() {

        viewModelScope.launch {
            kotlin.runCatching {
                eventRepository.listEventsByStatus("submitted")
            }.onSuccess {
                pendingEvents.value = it
                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun refresh() {
        listPendingEvent()
    }
}