package com.goddoro.udc.views.classShop.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Star
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/10/31
 */

class ClassDetailViewModel(
    val danceClass : DanceClass,
    val classRepository: ClassRepository
) : ViewModel() {

    val artistProfiles : MutableLiveData<List<Int>> = MutableLiveData()

    val star : MutableLiveData<Float> = MutableLiveData(danceClass.star?.point)

    val askTitle : MutableLiveData<String> = MutableLiveData()
    val askBody : MutableLiveData<String> = MutableLiveData()


    val clickInstagram : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickYoutube : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickAskButton : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickRatingButton : MutableLiveData<Once<Unit>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        artistProfiles.value = listOf(
            R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4
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

    fun onClickRatingButton() {
        clickRatingButton.value = Once(Unit)
    }


}