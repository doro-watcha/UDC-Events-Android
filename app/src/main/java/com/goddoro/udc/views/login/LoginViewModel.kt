package com.goddoro.udc.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.udc.di.NetworkClient
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/15
 */

class LoginViewModel @Inject constructor() : ViewModel() {

    private val TAG = LoginViewModel::class.java.simpleName

    // region DATA

    val username : MutableLiveData<String> = MutableLiveData()

    val password : MutableLiveData<String> = MutableLiveData()


    val loginCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val loginFailed : MutableLiveData<Once<Unit>> = MutableLiveData()
    // endregion


    init {


    }


    fun onClickLogin () {


        viewModelScope.launch {
            kotlin.runCatching {
                Log.e(TAG, "uername = ${username.value}")
                Log.e(TAG, "password = ${password.value}")
                NetworkClient.userService.signin(username.value!!, password.value!!)
            }.onSuccess {
                Log.e(TAG, it.toString())
                loginCompleted.value = Once(Unit)
            }.onFailure {

                Log.e(TAG, it.toString())
                loginFailed.value = Once(Unit)
            }
        }
    }


}