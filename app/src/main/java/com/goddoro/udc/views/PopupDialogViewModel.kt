package com.goddoro.udc.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.common.util.AppPreference
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PopupDialogViewModel(
    private val classRepository: ClassRepository,
    private val appPreference: AppPreference
) : ViewModel() {

    val isChecked: MutableLiveData<Boolean> = MutableLiveData()
    val popupClassLoadCompleted : MutableLiveData<Once<DanceClass>> = MutableLiveData()
    val clickDismissButton: MutableLiveData<Once<Unit>> = MutableLiveData()

    init {
        getPopupClass()
    }

    fun onClickDismiss() {
        clickDismissButton.value = Once(Unit)
    }

    fun onClickNoMoreSee() {
        isChecked.value = !(isChecked.value ?: false)
    }

    fun checkPopup() {
        if ( isChecked.value == true) {

            val dt = Date()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.format(dt).toString()

            appPreference.popUpDate = date

        }
    }


    private fun getPopupClass() {

        viewModelScope.launch {
            kotlin.runCatching {
                classRepository.getPopupClass()
            }.onSuccess {
                popupClassLoadCompleted.value = Once(it)
            }.onFailure {

            }

        }


    }
}