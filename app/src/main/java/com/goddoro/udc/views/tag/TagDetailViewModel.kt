package com.goddoro.udc.views.tag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository


/**
 * created By DORO 2020/10/17
 */


class TagDetailViewModel (
    private val authRepository : AuthRepository
) : ViewModel() {

    private val curUser : MutableLiveData<User> = MutableLiveData(authRepository.curUser.value!!)

    val artistQuery : MutableLiveData<String> = MutableLiveData()
    val genreQuery : MutableLiveData<String> = MutableLiveData()



}