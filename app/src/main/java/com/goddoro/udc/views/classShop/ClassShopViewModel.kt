package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.*
import com.goddoro.common.data.model.Date
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.common.data.repository.GenreRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch
import java.util.*


/**
 * created By DORO 2020/10/24
 */

class ClassShopViewModel(
    private val classRepository: ClassRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {

    private val TAG = ClassShopViewModel::class.java.simpleName


    val mainClasses: MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val dayOfClasses: MutableLiveData<List<DanceClass>> = MutableLiveData()

    val dateList: MutableLiveData<ArrayList<Date>> = MutableLiveData(ArrayList())

    var curDayIndex = 0

    val genres: MutableLiveData<List<Genre>> = MutableLiveData()

    val onGenreLoadCompleted : MutableLiveData<Boolean> = MutableLiveData()
    val clickSearchClass : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked: MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listMainClasses()
        setupDateList()
        listGenres()

//        mainClasses.value = listOf(
//            DanceClass(0,"zxcv",null,null,true,"zxcv","zxcv","zxcv","zxcv",0,"zxcv","zxcv","zxcv","zxcv","zxcv",
//                Academy(0,"zxcv","zxcv","zxcv",0.0,0.0),Genre(1,"zxcv"),listOf(SubImage(0,"zxcv")),null,temporaryImage = R.drawable.class_sample_1),
//            DanceClass(0,"zxcv",null,null,true,"zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv",
//                Academy(0,"zxcv","zxcv","zxcv",0.0,0.0),Genre(1,"zxcv"),listOf(SubImage(0,"zxcv")),null,temporaryImage = R.drawable.class_sample_2),
//            DanceClass(0,"zxcv",null,null,true,"zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv",
//                Academy(0,"zxcv","zxcv","zxcv",0.0,0.0),Genre(1,"zxcv"),listOf(SubImage(0,"zxcv")),null,temporaryImage = R.drawable.class_sample_3),
//            DanceClass(0,"zxcv",null,null,true,"zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv",
//                Academy(0,"zxcv","zxcv","zxcv",0.0,0.0),Genre(1,"zxcv"),listOf(SubImage(0,"zxcv")),null,temporaryImage = R.drawable.class_sample_4),
//            DanceClass(0,"zxcv",null,null,true,"zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv","zxcv",
//                Academy(0,"zxcv","zxcv","zxcv",0.0,0.0),Genre(1,"zxcv"),listOf(SubImage(0,"zxcv")),null,temporaryImage = R.drawable.class_sample_4)
//            )
//


    }

    private fun listMainClasses() {
        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(sort = "main")
            }.onSuccess {
                debugE(TAG, it)
                mainClasses.value = it
            }.onFailure {
                debugE(TAG, "1")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun setupDateList() {

        val today = Calendar.getInstance()

        for (i in 0..7) {
            val today_day = today.get(Calendar.DATE)
            val today_date = when (today.get(Calendar.DAY_OF_WEEK)) {

                1 -> "일"
                2 -> "월"
                3 -> "화"
                4 -> "수"
                5 -> "목"
                6 -> "금"
                7 -> "토"
                else -> throw Error()
            }
            dateList.value?.add(Date(date = today_date, day = today_day, dateInt = today.get(Calendar.DAY_OF_WEEK) - 1))
            today.add(Calendar.DATE, 1)
        }
    }

    fun listDateClasses( date : Date) {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(
                    day = date.dateInt
                )
            }.onSuccess {
                dayOfClasses.value = it
                debugE(TAG, "WOw "+ it )
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun listGenres() {

        viewModelScope.launch {

            kotlin.runCatching {
                genreRepository.listGenre()
            }.onSuccess {
                genres.value = it
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    fun onClickSearchClass() {
        clickSearchClass.value = Once(Unit)
    }


}