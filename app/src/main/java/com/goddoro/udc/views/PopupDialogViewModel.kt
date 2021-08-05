package com.goddoro.udc.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.ClassRepository

class PopupDialogViewModel(private val classRepository: ClassRepository) : ViewModel() {

    val isChecked : MutableLiveData<Boolean> = MutableLiveData()

    val clickDismissButton : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun onClickDismiss() {
        clickDismissButton.value = Once(Unit)
    }

    fun onClickNoMoreSee() {
        isChecked.value = !( isChecked.value ?: false)
    }
}