package com.goddoro.udc.views.home

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
 * created By DORO 2020/08/16
 */

class HomeViewModel (
    private val eventRepository: EventRepository
): ViewModel() {

    private val TAG = HomeViewModel::class.java.simpleName


    val mainEvents : MutableLiveData<List<Event>> = MutableLiveData()

    val newEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val upComingEvents : MutableLiveData<List<Event?>> = MutableLiveData()

    val hotEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val udcEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val staffPickEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val curPoster : MutableLiveData<Event?> = MutableLiveData()

    val location : MutableLiveData<List<String>?> = MutableLiveData()

    val isMapReady : MutableLiveData<Boolean?> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    init {

        debugE(TAG,"GODO")

        location.value = listOf ( "경기도 성남시 분당구 불정로 6 NAVER그린팩토리", "서울특별시 중구 세종대로 110 서울특별시청","서울특별시 중구 세종대로 101")


        getMainEvents()
        getNewEvents()
        getUpcomingEvents()
    }

    private fun getMainEvents() {

        viewModelScope.launch {
            kotlin.runCatching {

                eventRepository.getMainEventList()

            }.onSuccess{

                mainEvents.value = it

            }.onFailure {
                errorInvoked.value = Once(it)

            }
        }
    }

    private fun getNewEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.getNewEventList()
            }.onSuccess {
                debugE(TAG,it)
                newEvents.value= it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }


    private fun getUpcomingEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.getUpcomingEventList()
            }.onSuccess {
                debugE(TAG, "Upcoming Events = " + it)
                upComingEvents.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }

    }
}