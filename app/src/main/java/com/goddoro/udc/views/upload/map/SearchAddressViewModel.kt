package com.goddoro.udc.views.upload.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.Broadcast
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.repository.NaverRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 4/25/21
 */

class SearchAddressViewModel (
    private val naverRepository : NaverRepository
): ViewModel() {

    private val TAG = SearchAddressViewModel::class.java.simpleName


    val currentAddress : MutableLiveData<String> = MutableLiveData()
    val isValidAddress : MutableLiveData<Boolean> = MutableLiveData()


    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()



    fun onCameraChange ( latitude : Double, longitude : Double) {

        debugE(TAG, "Let'sgo change")

        viewModelScope.launch{
            kotlin.runCatching {
                naverRepository.getAddressFromLocation(latitude,longitude)
            }.onSuccess {

                if ( it.status.name == "ok") {
                    currentAddress.value = it.results[0].let {
                        it
                        it.region.area1.name + " " + it.region.area2.name + " " + it.region.area3.name + " " + it.region.area4.name + " " + it.land.name + " " + it.land.number1

                    }
                    isValidAddress.value = true
                }
                else {
                    isValidAddress.value = false
                    currentAddress.value = "좀 더 줌을 땡겨서 정확한 주소를 찾아주세요!"
                }
                debugE("NAVER", currentAddress.value)

            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }




    }

    fun onClickConfirm() {
        clickConfirm.value = Once(Unit)

    }

}