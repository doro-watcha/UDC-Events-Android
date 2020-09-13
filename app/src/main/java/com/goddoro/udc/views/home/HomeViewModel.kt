package com.goddoro.udc.views.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.debugE
import com.goddoro.common.data.data.Event
import com.goddoro.udc.R
import javax.inject.Inject


/**
 * created By DORO 2020/08/16
 */

class HomeViewModel @Inject constructor() : ViewModel() {

    private val TAG = HomeViewModel::class.java.simpleName

    val posters : MutableLiveData<List<Event>?> = MutableLiveData()

    val newEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val hotEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val udcEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val staffPickEvents : MutableLiveData<List<Event>?> = MutableLiveData()

    val curPoster : MutableLiveData<Event?> = MutableLiveData()

    val location : MutableLiveData<List<String>?> = MutableLiveData()

    val isMapReady : MutableLiveData<Boolean?> = MutableLiveData()


    init {

        debugE(TAG,"GODO")
        posters.value = listOf (
            Event(0, "https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png",
                R.drawable.sample_image_01,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png"),
            Event(1,"https://cdn.beatflo.co/video/e36vy8e35gm_1595331665924.jpg",
                R.drawable.sample_image_02),
            Event(2,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png",
                R.drawable.sample_image_03,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png"),
            Event(3,"https://cdn.beatflo.co/video/e36vy8e35gm_1595331665924.jpg",
                R.drawable.sample_image_04),
            Event(0, "https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png",
                R.drawable.sample_image_01,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png"),
            Event(1,"https://cdn.beatflo.co/video/e36vy8e35gm_1595331665924.jpg",
                R.drawable.sample_image_02),
            Event(2,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png",
                R.drawable.sample_image_03,"https://cdn.beatflo.co/video/jrfc39guu2_1584445595815.png"),
            Event(3,"https://cdn.beatflo.co/video/e36vy8e35gm_1595331665924.jpg", R.drawable.sample_image_04)
        )
        curPoster.value = posters.value!![0]

        location.value = listOf ( "경기도 성남시 분당구 불정로 6 NAVER그린팩토리", "서울특별시 중구 세종대로 110 서울특별시청","서울특별시 중구 세종대로 101")

        newEvents.value = listOf(
            Event(0),
            Event(1),
            Event(2),
            Event(0),
            Event(1),
            Event(2)
        )

        hotEvents.value = listOf(
            Event(0),
            Event(1),
            Event(1),
            Event(1),
            Event(1),
            Event(1)
        )

        udcEvents.value = listOf(
            Event(0),
            Event(1),
            Event(2),
            Event(0),
            Event(1),
            Event(2)


        )

        staffPickEvents.value = listOf(
            Event(0),
            Event(1),
            Event(0),
            Event(1),
            Event(0),
            Event(1)
        )
    }
}