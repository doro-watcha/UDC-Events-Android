package com.goddoro.udc.views.profile.star

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository

class MyStarViewModel ( private val classRepository: ClassRepository) : ViewModel(){

    val starClasses : MutableLiveData<List<DanceClass>> = MutableLiveData()
}