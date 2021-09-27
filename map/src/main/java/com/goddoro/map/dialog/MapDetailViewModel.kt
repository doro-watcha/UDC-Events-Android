package com.goddoro.map.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import kotlinx.coroutines.launch

class MapDetailViewModel ( private val classRepository: ClassRepository) : ViewModel() {

    lateinit var academy : Academy

    val classes : MutableLiveData<List<DanceClass>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    fun init ( academy: Academy ) {
        this.academy = academy
        listClasses()
    }

    private fun listClasses() {
        viewModelScope.launch {
            kotlin.runCatching {
              classRepository.listClasses(academyId = academy.id)
            }.onSuccess {
                classes.value = it
            }.onFailure {
                errorInvoked.value = it
            }
        }

    }
}