package com.goddoro.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.NaverItem
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.data.repository.NaverRepository
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventMapViewModel (
    private val eventRepository: EventRepository,
    private val naverRepository: NaverRepository
): ViewModel(){

    private val TAG = EventMapViewModel::class.java.simpleName

    val events : MutableLiveData<List<Event>> = MutableLiveData()

    val query : MutableLiveData<String> = MutableLiveData()
    val searchedEvents : MutableLiveData<List<Event>> = MutableLiveData()


    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {
    }

    fun listEvents() {
        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.listEventsBySort("new")
            }.onSuccess {
                debugE(TAG,it.filter{it.latitude!! > 5.0})
                events.value = it.filter { it.latitude!! > 5.0}
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun onQueryChanged ( query : String) {
        debugE(TAG, query)
        if (query.isNotEmpty()) {
            searchedEvents.value = events.value?.filter { it.name?.contains(query) ?: false }
        }
    }


}