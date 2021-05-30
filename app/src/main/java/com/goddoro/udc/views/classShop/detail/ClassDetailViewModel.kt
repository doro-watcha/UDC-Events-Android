package com.goddoro.udc.views.classShop.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/10/31
 */

class ClassDetailViewModel(
    val danceClass : DanceClass,
    val classRepository: ClassRepository
) : ViewModel() {


    val artistProfiles : MutableLiveData<List<String>> = MutableLiveData()

    val askTitle : MutableLiveData<String> = MutableLiveData()
    val askBody : MutableLiveData<String> = MutableLiveData()


    val clickInstagram : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickYoutube : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickAskButton : MutableLiveData<Once<Unit>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        artistProfiles.value = listOf(
            "zxcv","zxcv","zxcv"
        )

    }



    fun onClickInstagram() {
        clickInstagram.value = Once(Unit)

    }

    fun onClickYoutube() {
        clickYoutube.value = Once(Unit)

    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickAskButton() {

        clickAskButton.value = Once(Unit)
    }


}