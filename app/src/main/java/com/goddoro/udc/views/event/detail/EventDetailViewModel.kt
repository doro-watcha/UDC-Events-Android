package com.goddoro.udc.views.event.detail

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
 * created By DORO 2020/10/02
 */

class EventDetailViewModel (
    val eventId : Int ,
    val eventRepository: EventRepository
) : ViewModel() {


    private val TAG = EventDetailViewModel::class.java.simpleName

    val curEvent : MutableLiveData<Event> = MutableLiveData()

    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {

        getEvent()


    }

    fun getEvent () {

        viewModelScope.launch {
            kotlin.runCatching {
                eventRepository.getEvent(eventId)
            }.onSuccess {
                debugE(TAG, it )
                curEvent.value = it
            }.onFailure {
                debugE(TAG, it.message)
            }
        }
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }
}


