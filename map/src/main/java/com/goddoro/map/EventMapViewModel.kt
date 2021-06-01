package com.goddoro.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.NaverItem
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.data.repository.NaverRepository
import com.naver.maps.map.NaverMap
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventMapViewModel (
    private val eventRepository: EventRepository,
    private val naverRepository: NaverRepository
): ViewModel(){

    private val TAG = EventMapViewModel::class.java.simpleName

    val events : MutableLiveData<List<Event>> = MutableLiveData()


    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()
    val clickTest : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {

      //  listEvents()
        getLocation()
    }

    fun listEvents() {
        viewModelScope.launch {


            kotlin.runCatching {
                eventRepository.listEventsBySort("upcoming")
            }.onSuccess {
                events.value = it
            }.onFailure {

                errorInvoked.value = it
            }
        }
    }

    fun getLocation() {

        viewModelScope.launch {

            kotlin.runCatching {
                naverRepository.getLocationFromAddress("경기도 성남시 분당구 불정로 6 그린팩토리")
            }.onSuccess {
                debugE(TAG,it.addresses)
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

    fun onClickTest() {
        clickTest.value = Once(Unit)
    }
}