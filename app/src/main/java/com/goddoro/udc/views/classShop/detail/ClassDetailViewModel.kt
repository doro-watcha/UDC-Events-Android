package com.goddoro.udc.views.classShop.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Star
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


/**
 * created By DORO 2020/10/31
 */

class ClassDetailViewModel(
    val classId : Int,
    val classRepository: ClassRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val artistProfiles : MutableLiveData<List<Int>> = MutableLiveData()

    val danceClass : MutableLiveData<DanceClass> = MutableLiveData()

    val star = MediatorLiveData<Float>().apply {

        addSource(danceClass){
            this.value = it.star?.get(0)?.point
        }
    }

    val average = MediatorLiveData<Float>().apply {

        addSource(danceClass){
            this.value = ( it.ratingPoint * 10 ).roundToInt() / 10f
        }
    }

    val askTitle : MutableLiveData<String> = MutableLiveData()
    val askBody : MutableLiveData<String> = MutableLiveData()


    val onLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val clickInstagram : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickYoutube : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickAskButton : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickRatingButton : MutableLiveData<Once<Unit>> = MutableLiveData()
    val needLogin : MutableLiveData<Once<Unit>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        artistProfiles.value = listOf(
            R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4
        )
        getClass()

    }

    private fun getClass() {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.getClass(classId)

            }.onSuccess {
                danceClass.value = it
                onLoadCompleted.value = true
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
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
        if ( authRepository.isSignedIn() ) needLogin.value = Once(Unit)
        else clickRatingButton.value = Once(Unit)
    }


}