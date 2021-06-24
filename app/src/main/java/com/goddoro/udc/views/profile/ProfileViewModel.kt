package com.goddoro.udc.views.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/16
 */

class ProfileViewModel (
    val authorId : Int ,
    val userRepository : UserRepository,
    val authRepository: AuthRepository
) : ViewModel() {

    val author : MutableLiveData<User> = MutableLiveData()

    val clickSetting : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickNotification : MutableLiveData<Once<Unit>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        if (authRepository.isSignedIn()) getUser()

    }


    private fun getUser () {

        viewModelScope.launch{

            kotlin.runCatching {
                userRepository.getUser(authorId)
            }.onSuccess {
                author.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }


    }

    fun refresh() {
        getUser()
    }

    fun onClickSetting() {
        clickSetting.value = Once(Unit)
    }

    fun onClickNotification () {
        clickNotification.value = Once(Unit)
    }




}