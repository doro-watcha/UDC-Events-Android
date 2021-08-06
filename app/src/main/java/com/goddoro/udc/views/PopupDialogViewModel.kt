package com.goddoro.udc.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.common.util.AppPreference
import java.text.SimpleDateFormat
import java.util.*

class PopupDialogViewModel(
    private val classRepository: ClassRepository,
    private val appPreference: AppPreference
) : ViewModel() {

    val isChecked: MutableLiveData<Boolean> = MutableLiveData()

    val clickDismissButton: MutableLiveData<Once<Unit>> = MutableLiveData()


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
}