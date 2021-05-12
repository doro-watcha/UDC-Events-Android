package com.goddoro.udc.views.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once


/**
 * created By DORO 11/21/20
 */

class SearchViewModel : ViewModel() {



    val query : MutableLiveData<String> = MutableLiveData()

    val histories : MutableLiveData<List<String>> = MutableLiveData()

    val recommendations : MutableLiveData<List<String>> = MutableLiveData()

    val clickBackArrow: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCurrentQuery : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {

        histories.value = listOf( "BBIC","GOOD MAN", "SINCHON PEOPLE")

        recommendations.value = listOf("BAD MAN", "ALFJALKDF ", "POLE POLE", "HONGDAE " , " JI YOUNG AHN ")
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickCurrentQuery() {
        clickCurrentQuery.value = Once(Unit)
    }
}