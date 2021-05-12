package com.goddoro.udc.views.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventCollectionViewModel (
    private val eventRepository: EventRepository
) : ViewModel() {

    private val TAG = EventCollectionViewModel::class.java.simpleName

    val events: MutableLiveData<List<Event>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        debugE(TAG, "EventCollecrionViewModel")
        listMyEvents()

    }

    private fun listMyEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.listEventsByStatus("all")
            }.onSuccess {
                debugE(TAG, "My Events = " + it)
                events.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }

    }
}