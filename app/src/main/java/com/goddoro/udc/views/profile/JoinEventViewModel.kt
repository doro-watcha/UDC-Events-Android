package com.goddoro.udc.views.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Event
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class JoinEventViewModel: ViewModel() {

    val events : MutableLiveData<List<Event>> = MutableLiveData()

    init {

    }

}