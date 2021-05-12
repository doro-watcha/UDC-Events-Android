package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/10/24
 */

class ClassShopViewModel(
    val classRepository : ClassRepository
) : ViewModel() {

    private val TAG = ClassShopViewModel::class.java.simpleName


    val mainClasses: MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val normalClasses : MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val workshopClasses : MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listMainClasses()
        listNormalClasses()
        listWorkshopClasses()

    }

    private fun listMainClasses() {
        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(isMainClass = true)
            }.onSuccess {
                debugE(TAG,it)
                mainClasses.value = it
            }.onFailure {
                debugE(TAG, "1")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun listNormalClasses() {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(type = "regular")
            }.onSuccess{
                normalClasses.value = it
            }.onFailure {
                debugE(TAG,"2")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun listWorkshopClasses () {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(type = "popup")
            }.onSuccess {
                workshopClasses.value = it
            }.onFailure {
                debugE(TAG,"3")
                errorInvoked.value = Once(it)
            }
        }
    }

}