package com.goddoro.udc.views.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/16
 */

class HomeViewModel (
    private val eventRepository: EventRepository,
    val authRepository: AuthRepository
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

    val clickSearch : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickUpload : MutableLiveData<Once<Unit>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    // region DATA


    init {

        debugE(TAG,"GODO")

        location.value = listOf ( "경기도 성남시 분당구 불정로 6 NAVER그린팩토리", "서울특별시 중구 세종대로 110 서울특별시청","서울특별시 중구 세종대로 101")


        fetchData()


    }

    private fun fetchData() {

        getMainEvents()
        getNewEvents()
        getUpcomingEvents()
        getHotEvents()
        getRecommendEvents()
    }

    fun refresh() {
        fetchData()
    }
    private fun getMainEvents() {

        viewModelScope.launch {
            kotlin.runCatching {

                eventRepository.listEventsBySort("main")

            }.onSuccess{

                debugE(TAG, "MAIN EVENT LOAD COMPLETED")

                debugE(TAG, it)

                mainEvents.value = it

            }.onFailure {
                errorInvoked.value = Once(it)

            }
        }
    }

    private fun getNewEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.listEventsBySort("new")
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
                eventRepository.listEventsBySort("upcoming")
            }.onSuccess {
                debugE(TAG, "Upcoming Events = " + it)
                upComingEvents.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }

    }

    private fun getHotEvents () {

        viewModelScope.launch {

            kotlin.runCatching {
                eventRepository.listEventsBySort("hot")
            }.onSuccess {
                debugE(TAG, "HOT EVENTS")
                hotEvents.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun getRecommendEvents () {

        viewModelScope.launch{

            kotlin.runCatching {
                eventRepository.listEventsBySort("recommendation")
            }.onSuccess {
                debugE(TAG, "STAFF PICK EVENTS")
                staffPickEvents.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    fun onClickSearch() {
        clickSearch.value = Once(Unit)
    }
    fun onClickUpload() {
        clickUpload.value = Once(Unit)
    }
}