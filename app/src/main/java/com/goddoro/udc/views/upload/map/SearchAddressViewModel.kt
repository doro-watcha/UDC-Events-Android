package com.goddoro.udc.views.upload.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.Broadcast
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.api.LocationResponse
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
    val query : MutableLiveData<String> = MutableLiveData()
    var query_x = 0.0
    var query_y = 0.0
    val roadAddress : MutableLiveData<String> = MutableLiveData("")
    val jibunAddress : MutableLiveData<String> = MutableLiveData("")
    val isValidAddress : MutableLiveData<Boolean> = MutableLiveData()


    val isAddressEditable : MutableLiveData<Boolean> = MutableLiveData(false)
    val clickConfirm : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCancelEdit : MutableLiveData<Once<Unit>> = MutableLiveData()

    val clickAddress : MutableLiveData<Once<Unit>> = MutableLiveData()
    val findSuccessAddress : MutableLiveData<LocationResponse> = MutableLiveData()
    val findWrongAddress : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()


    fun findLocation( address : String ) {

        if ( address == "") return

        viewModelScope.launch {

            kotlin.runCatching {
                naverRepository.getLocationFromAddress(address)
            }.onSuccess {
                if ( it.meta.totalCount >= 1 ) {
                    roadAddress.value = it.addresses[0].roadAddress
                    jibunAddress.value = it.addresses[0].jibunAddress
                    query_x = it.addresses[0].y.toDouble()
                    query_y = it.addresses[0].x.toDouble()
                }
                else {
                    findWrongAddress.value = Once(Unit)
                }

            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    fun onCameraChange ( latitude : Double, longitude : Double) {

        debugE(TAG, "Let'sgo change")

        viewModelScope.launch{
            kotlin.runCatching {
                naverRepository.getAddressFromLocation(latitude,longitude)
            }.onSuccess {

                if ( it.status.name == "ok") {
                    currentAddress.value = it.results[0].let {
                        it
                        it.region.area1.name + " " + it.region.area2.name + " " + it.land.name + " " + it.land.number1 + it.land.number2

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
    fun onClickEditButton() {
        if ( isAddressEditable.value == false) {
            isAddressEditable.value = true
        } else {
            findLocation(currentAddress.value ?: "")
        }
    }

    fun onClickCancelEdit() {
        isAddressEditable.value = false
    }

    fun onClickAddress() {
        currentAddress.value = roadAddress.value
        query.value = ""
        jibunAddress.value = ""
        roadAddress.value = ""

        clickAddress.value = Once(Unit)
    }

}