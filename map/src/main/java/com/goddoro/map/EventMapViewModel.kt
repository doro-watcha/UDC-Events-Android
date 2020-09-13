package com.goddoro.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.data.Event
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventMapViewModel @Inject constructor() : ViewModel(){

    val events : MutableLiveData<List<Event>> = MutableLiveData()

    init {

        events.value = listOf(Event(0,location = "서울 마포구 신촌로 180"),Event(1,location = "서울 마포구 신촌로 90"))

    }
}