package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Artist
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch


/**
 * created By DORO 2020/10/24
 */

class ClassShopViewModel(
    val classRepository : ClassRepository
) : ViewModel() {

    private val TAG = ClassShopViewModel::class.java.simpleName


    val mainClasses: MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val normalClasses : MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val workshopClasses : MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val mainClassPosition : MutableLiveData<Int> = MutableLiveData()

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

//        listMainClasses()
//        listNormalClasses()
//        listWorkshopClasses()

        mainClasses.value = listOf(
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn","BoolBbalgan Music"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_02),
            DanceClass(0,"zxcv", Artist(0,"Yuree Choi","Solo"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_05),
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn","BoolBbalgan Music"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_02),
            DanceClass(0,"zxcv", Artist(0,"Yuree Choi","Solo"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_05)
        )

        normalClasses.value = listOf(
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_01),
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_01)
        )

        workshopClasses.value = listOf(
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_01),
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn"),"zxcvzxcv","zxcvzxcv","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_01)
        )

    }

    private fun listMainClasses() {
        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(isMainClass = true)
            }.onSuccess {
                debugE(TAG,it)
                mainClasses.value = it
            }.onFailure {
                debugE(TAG, "1")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun listNormalClasses() {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(type = "regular")
            }.onSuccess{
                normalClasses.value = it
            }.onFailure {
                debugE(TAG,"2")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun listWorkshopClasses () {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(type = "popup")
            }.onSuccess {
                workshopClasses.value = it
            }.onFailure {
                debugE(TAG,"3")
                errorInvoked.value = Once(it)
            }
        }
    }

}