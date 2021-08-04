package com.goddoro.udc.views.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.ClassRepository


/**
 * created By DORO 11/21/20
 */

class SearchViewModel(
    private val classRepository: ClassRepository
) : ViewModel() {


    val query: MutableLiveData<String> = MutableLiveData()

    val histories: MutableLiveData<List<String>> = MutableLiveData()

    val recommendations: MutableLiveData<List<String>> = MutableLiveData()

    val clickBackArrow: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCurrentQuery: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickFilterButton : MutableLiveData<Once<Unit>> = MutableLiveData()

    val onSearchCompleted: MutableLiveData<Boolean> = MutableLiveData()

    init {

        histories.value = listOf("BBIC", "GOOD MAN", "SINCHON PEOPLE")

        recommendations.value =
            listOf("BAD MAN", "ALFJALKDF ", "POLE POLE", "HONGDAE ", " JI YOUNG AHN ")
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickCurrentQuery() {
        clickCurrentQuery.value = Once(Unit)
    }

    fun onClickFilterButton(){
        clickFilterButton.value = Once(Unit)
    }
}