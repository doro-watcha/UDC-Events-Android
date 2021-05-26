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

    val curSketchImages = listOf(R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4, R.drawable.s5)

    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPoster : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {



    }


    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickPoster () {
        clickPoster.value = Once(Unit)
    }
}


