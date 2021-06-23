package com.goddoro.udc.views.event.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    init {

        debugE(TAG, curEvent.value)


    }


    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickPoster () {
        clickPoster.value = Once(Unit)
    }
}


