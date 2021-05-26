package com.goddoro.udc.views.admin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 2/15/21
 */

class AdminViewModel(private val eventRepository: EventRepository) : ViewModel() {


    private val TAG = AdminViewModel::class.java.simpleName

    val artistEmail: MutableLiveData<String> = MutableLiveData()


    val unConfirmedEvent: MutableLiveData<List<Event>> = MutableLiveData()

    val onGrantSuccess : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {
        listUnConfirmedEvents()
    }

    private fun listUnConfirmedEvents() {

        viewModelScope.launch {


            kotlin.runCatching {
                eventRepository.listEventsByStatus("submitted")
            }.onSuccess {
                unConfirmedEvent.value = it
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun refresh () {

        listUnConfirmedEvents()
    }

    fun onClickConfirm ( event : Event) {

        Log.d(TAG,"여기까지 왔어..")
        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.updateStatus(event.id,"granted")
            }.onSuccess {
                onGrantSuccess.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }


}