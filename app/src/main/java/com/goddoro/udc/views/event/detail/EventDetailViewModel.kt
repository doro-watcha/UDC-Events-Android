package com.goddoro.udc.views.event.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository


/**
 * created By DORO 2020/10/02
 */

class EventDetailViewModel (
    val event : Event ,
    val eventRepository: EventRepository
) : ViewModel() {


    private val TAG = EventDetailViewModel::class.java.simpleName

    val curEvent : MutableLiveData<Event> = MutableLiveData(event)

    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPoster : MutableLiveData<Once<Unit>> = MutableLiveData()

    val onPlannerPressed = MediatorLiveData<Boolean>().apply {

        addSource(curEvent) {
            this.value = it.isLike ?: false
        }
    }
    init {

        debugE(TAG, curEvent.value)


    }


    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickPoster () {
        clickPoster.value = Once(Unit)
    }

    fun onClickPlanner() {
        onPlannerPressed.value = !(onPlannerPressed.value ?: false )
    }
}


