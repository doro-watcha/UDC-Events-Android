package com.goddoro.udc.views.setting

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Tag
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository


/**
 * created By DORO 2020/10/10
 */

class SettingViewModel (
    private val authRepository: AuthRepository
): ViewModel() {

    // region DATA

    private val curUser : MutableLiveData<User?> = MutableLiveData(authRepository.curUser.value)

    val email = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.email
        }
    }

    val username = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.username
        }
    }

    val nickName = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.username
        }
    }

    val location = MediatorLiveData<String>().apply {
        addSource(curUser){
            this.value = it?.location
        }
    }

    val followArtist = MediatorLiveData<List<Tag>>().apply {
        addSource(curUser){
            this.value = it?.followArtists
        }
    }

    val genres = MediatorLiveData<List<String>>().apply {
        addSource(curUser){
            this.value = it?.genres
        }
    }

    // endregion

    // region STATE

    // endregion


    // region EVENT

    val clickTagDetailDialog : MutableLiveData<Once<Int>> = MutableLiveData()

    // endregion


    val clickLogOut : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun onClickLogOut() {

        clickLogOut.value = Once(Unit)
    }

    fun onClickTagDetail( position : Int) {
        clickTagDetailDialog.value = Once(position)
    }

}