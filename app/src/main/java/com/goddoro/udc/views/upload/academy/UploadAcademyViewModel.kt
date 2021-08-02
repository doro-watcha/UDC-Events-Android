package com.goddoro.udc.views.upload.academy

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.repository.AcademyRepository
import com.goddoro.common.data.repository.NaverRepository
import kotlinx.coroutines.launch

class UploadAcademyViewModel(
    private val academyRepository: AcademyRepository,
    private val naverRepository: NaverRepository
) : ViewModel() {

    private val TAG = UploadAcademyViewModel::class.java.simpleName

    val name: MutableLiveData<String> = MutableLiveData("test")

    val logoImage: MutableLiveData<Uri> = MutableLiveData()

    val location: MutableLiveData<String> = MutableLiveData()
    val isValidAddress : MutableLiveData<Boolean> = MutableLiveData()

    val isValidQuery = MediatorLiveData<Boolean>().apply {

        addSource(isValidAddress) {
            this.value = ( isValidAddress.value ?: false) && location.value?.length ?: 0 > 0
        }
        addSource(location) {
            this.value = ( isValidAddress.value ?: false) && location.value?.length ?: 0 > 0
        }
    }

    val isClickableRegisterButton = MediatorLiveData<Boolean>().apply {

        addSource(isValidQuery){
            this.value = ( isValidQuery.value ?: false) && name.value?.length ?: 0 > 0
        }
        addSource(name){
            this.value = ( isValidQuery.value ?: false) && name.value?.length ?: 0 > 0
        }
    }


    val clickGalleryButton: MutableLiveData<Once<Unit>> = MutableLiveData()
    val onRegisterComplete : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    var latitude : Double = 0.0
    var longitude : Double = 0.0

    fun onClickGalleryButton() {
        clickGalleryButton.value = Once(Unit)
    }

    fun onClickRegisterButton() {

        viewModelScope.launch {

            kotlin.runCatching {
                academyRepository.registerAcademy(
                    name = name.value ?: "",
                    location = location.value ?: "",
                    logoImage = logoImage.value,
                    latitude = latitude,
                    longitude = longitude
                )
            }.onSuccess {
                onRegisterComplete.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }
    fun findLocation ( address : String ) {

        debugE(TAG, address)
        debugE(TAG, location.value!! )

        if ( address == "" ) return

        viewModelScope.launch {

            kotlin.runCatching {
                naverRepository.getLocationFromAddress(address)
            }.onSuccess {
                if ( it.meta.totalCount >= 1 ) {
                    //location.value = it.addresses[0].roadAddress
                    latitude = it.addresses[0].y.toDouble()
                    longitude = it.addresses[0].x.toDouble()
                    isValidAddress.value = true
                }
                else {
                    isValidAddress.value = false
                }

            }.onFailure {
                errorInvoked.value = it
            }
        }
    }

}