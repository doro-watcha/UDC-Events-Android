package com.goddoro.udc.views.profile.collection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/09/12
 */

class EventCollectionViewModel (
    private val eventRepository: EventRepository
) : ViewModel() {

    private val TAG = EventCollectionViewModel::class.java.simpleName

    val events: MutableLiveData<List<Event>> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {


        listMyEvents()

    }

    private fun listMyEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.listEventsByStatus("granted")
            }.onSuccess {
                onLoadCompleted.value = true
                events.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }

    }

    fun refresh() {

        listMyEvents()
    }
}