package com.goddoro.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Event
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventMapViewModel: ViewModel(){

    val events : MutableLiveData<List<Event>> = MutableLiveData()


    val clickTest : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {


    }

    fun onClickTest() {
        clickTest.value = Once(Unit)
    }
}