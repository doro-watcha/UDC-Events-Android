package com.goddoro.udc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import javax.inject.Inject


/**
 * created By DORO 2020/08/10
 */

class MainViewModel (
) : ViewModel() {



    val clickToLogin : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun onClickToLogin () {

        clickToLogin.value = Once(Unit)
    }
}