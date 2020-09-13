package com.goddoro.udc.views.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.data.Event
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class JoinEventViewModel @Inject constructor() : ViewModel() {

    val events : MutableLiveData<List<Event>> = MutableLiveData()

    init {

        events.value = listOf( Event(0), Event(1))
    }

}