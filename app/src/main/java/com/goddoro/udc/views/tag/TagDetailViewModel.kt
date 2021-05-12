package com.goddoro.udc.views.tag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Tag
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository


/**
 * created By DORO 2020/10/17
 */


class TagDetailViewModel (
    private val authRepository : AuthRepository
) : ViewModel() {

    val curUser : MutableLiveData<User> = MutableLiveData(authRepository.curUser.value!!)

    val myArtistTag : MutableLiveData<List<Tag>> = MutableLiveData()
    val myGenreTag : MutableLiveData<List<Tag>> = MutableLiveData()

    val artistQuery : MutableLiveData<String> = MutableLiveData()
    val genreQuery : MutableLiveData<String> = MutableLiveData()


    val queryArtistList : MutableLiveData<List<Tag>> = MutableLiveData()
    val queryGenreList : MutableLiveData<List<Tag>> = MutableLiveData()


    init {

        myArtistTag.value = listOf(Tag(0,"bboy-panic","Artist"),Tag(1,"bboy-code","Artist"),Tag(2,"bboy-SY","Artist"))
        myGenreTag.value = listOf(Tag(0,"B-Boy","Genre"),Tag(1,"Poppin","Genre"),Tag(2,"Locking","Genre"))

    }




}