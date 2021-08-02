package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.Artist
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Date
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.udc.R
import kotlinx.coroutines.launch
import java.util.*


/**
 * created By DORO 2020/10/24
 */

class ClassShopViewModel(
    private val classRepository : ClassRepository
) : ViewModel() {

    private val TAG = ClassShopViewModel::class.java.simpleName


    val mainClasses: MutableLiveData<List<DanceClass>?> = MutableLiveData()

    val dateList : MutableLiveData<ArrayList<Date>> = MutableLiveData(ArrayList())

    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {

        listMainClasses()
        setupDateList()

    }

    private fun listMainClasses() {
        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(sort = "main")
            }.onSuccess {
                debugE(TAG,it)
                mainClasses.value = it
            }.onFailure {
                debugE(TAG, "1")
                errorInvoked.value = Once(it)
            }
        }
    }

    private fun setupDateList () {

        val today = Calendar.getInstance()

        for ( i in 0..7){

            debugE(TAG, today.get(Calendar.DATE))
            debugE(TAG, today.get(Calendar.DAY_OF_WEEK))

            val today_day = today.get(Calendar.DATE)
            val today_date = when ( today.get(Calendar.DAY_OF_WEEK)){

                1 -> "일"
                2 -> "월"
                3 -> "화"
                4 -> "수"
                5 -> "목"
                6 -> "금"
                7 -> "토"
                else -> throw Error()
            }
            dateList.value?.add(Date(date = today_date, day = today_day))
            today.add(Calendar.DATE,1)

        }


    }

    fun listDateClasses() {


    }


}