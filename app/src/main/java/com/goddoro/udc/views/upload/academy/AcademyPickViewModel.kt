package com.goddoro.udc.views.upload.academy

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.repository.AcademyRepository
import kotlinx.coroutines.launch

class AcademyPickViewModel(
    private val academyRepository: AcademyRepository
) : ViewModel() {

    val academies: MutableLiveData<List<Academy>> = MutableLiveData()

    val selectedAcademy : MutableLiveData<Academy> = MutableLiveData()

    val isAcademySelected = MediatorLiveData<Boolean>().apply {

        addSource(selectedAcademy){
            this.value = it != null
        }
    }

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    fun listAcademies() {

        viewModelScope.launch {

            kotlin.runCatching {
                academyRepository.listAcademies()
            }.onSuccess {
                academies.value = it
            }.onFailure {
                errorInvoked.value = it
            }

        }
    }
}