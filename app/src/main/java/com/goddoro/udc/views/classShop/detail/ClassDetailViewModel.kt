package com.goddoro.udc.views.classShop.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.DanceClass


/**
 * created By DORO 2020/10/31
 */

class ClassDetailViewModel(
    val classId : Int
) : ViewModel() {


    val curDanceClass : MutableLiveData<DanceClass> = MutableLiveData()

    init {


    }




}