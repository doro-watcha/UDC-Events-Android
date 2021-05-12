package com.goddoro.udc.views.search.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.SearchRepository


/**
 * created By DORO 11/21/20
 */

class SearchDetailViewModel (
    val query : String,
    val searchRepository : SearchRepository
) : ViewModel() {

    val events : MutableLiveData<List<Event>> = MutableLiveData()


    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()

    init {


    }


    fun onClickBackArrow() {

        clickBackArrow.value = Once(Unit)
    }




}