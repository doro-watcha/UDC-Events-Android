package com.goddoro.udc.views.classShop.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.StarRepository
import kotlinx.coroutines.launch

class RatingViewModel (  val danceClass: DanceClass, private val starRepository: StarRepository ): ViewModel() {


    val ratingPoint : MutableLiveData<Float> = MutableLiveData()

    val onLoadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val onErrorInvoked : MutableLiveData<Throwable> = MutableLiveData(

    )

    private fun createStar () {

        viewModelScope.launch {

            kotlin.runCatching {
                starRepository.createStar(danceClass.id,ratingPoint.value ?: 0.5f)
            }.onSuccess {
                onLoadCompleted.value = Once(Unit)
            }.onFailure {
                onErrorInvoked.value = it
            }
        }


    }

    private fun deleteStar () {

        if ( danceClass.star == null ) return
        viewModelScope.launch {
            kotlin.runCatching {
                starRepository.deleteStar(danceClass.star?.id ?: 0)
            }.onSuccess {
                onLoadCompleted.value = Once(Unit)
            }.onFailure {
                onErrorInvoked.value = it
            }
        }
    }

    fun onClickConfirm() {

        if ( ratingPoint.value == 0.0f) {
            deleteStar()
        }
        else {
            createStar()
        }
    }
}