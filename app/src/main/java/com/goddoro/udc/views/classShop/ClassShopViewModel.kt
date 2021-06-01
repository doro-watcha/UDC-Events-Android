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
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn","BoolBbalgan Music"),"기초부터 차근차근","2020년 제9회 가온차트 뮤직 어워즈 디지털음원부문 올해의 가수상 4월\n" +
                    "2019년 엠넷 아시안 뮤직 어워즈 베스트 보컬 퍼포먼스 그룹\n" +
                    "2019년 멜론 뮤직 어워드 TOP 10","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_02),
            DanceClass(0,"zxcv", Artist(0,"Yuree Choi","Solo"),"zxcvzxcv","2020년 제9회 가온차트 뮤직 어워즈 디지털음원부문 올해의 가수상 4월\n" +
                    "2019년 엠넷 아시안 뮤직 어워즈 베스트 보컬 퍼포먼스 그룹\n" +
                    "2019년 멜론 뮤직 어워드 TOP 10","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_05),
            DanceClass(0,"zxcv", Artist(0,"Jiyoung Ahn","BoolBbalgan Music"),"zxcvzxcv","2020년 제9회 가온차트 뮤직 어워즈 디지털음원부문 올해의 가수상 4월\n" +
                    "2019년 엠넷 아시안 뮤직 어워즈 베스트 보컬 퍼포먼스 그룹\n" +
                    "2019년 멜론 뮤직 어워드 TOP 10","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_03),
            DanceClass(0,"zxcv", Artist(0,"Dudan","Solo"),"zxcvzxcv","2020년 제9회 가온차트 뮤직 어워즈 디지털음원부문 올해의 가수상 4월\n" +
                    "2019년 엠넷 아시안 뮤직 어워즈 베스트 보컬 퍼포먼스 그룹\n" +
                    "2019년 멜론 뮤직 어워드 TOP 10","zxcvzxcv","zxcvzxcv",true,"zxcvzxcv",
                R.drawable.sample_image_01)
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