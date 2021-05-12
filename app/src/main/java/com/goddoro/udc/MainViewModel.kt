package com.goddoro.udc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.repository.DeviceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/10
 */

class MainViewModel (
    val deviceRepository: DeviceRepository
) : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName


    val clickToLogin : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun onClickToLogin () {

        clickToLogin.value = Once(Unit)
    }



    fun registerDevice ( fcmToken : String ) {

        viewModelScope.launch {
            kotlin.runCatching {
                deviceRepository.registerDevice(fcmToken)
            }.onSuccess {
                debugE(TAG, "SUCCESS")
            }.onFailure {
                debugE(TAG, it.message)
            }
        }
    }

}