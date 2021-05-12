package com.goddoro.udc.views.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Event


/**
 * created By DORO 2/15/21
 */

class AdminViewModel : ViewModel() {


    val artistEmail : MutableLiveData<String> = MutableLiveData()


    val unConfirmedEvent : MutableLiveData<List<Event>> = MutableLiveData()


}