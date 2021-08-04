package com.goddoro.udc.views.upload.danceClass.schedule

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.Broadcast
import com.goddoro.common.common.Once

class SchedulePickViewModel : ViewModel() {

    val isMondayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isTuesdayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isWednesdayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isThursdayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isFridayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isSaturdayClicked: MutableLiveData<Boolean> = MutableLiveData()
    val isSundayClicked: MutableLiveData<Boolean> = MutableLiveData()

    val clickPickSchedule : MutableLiveData<Once<Unit>> = MutableLiveData()

    val isScheduleSelected = MediatorLiveData<Boolean>().apply {

        addSource(isMondayClicked) {
            this.value = isDaySelected()
        }
        addSource(isTuesdayClicked) {
            this.value = isDaySelected()
        }
        addSource(isWednesdayClicked) {
            this.value = isDaySelected()
        }
        addSource(isThursdayClicked) {
            this.value = isDaySelected()
        }
        addSource(isFridayClicked) {
            this.value = isDaySelected()
        }
        addSource(isSaturdayClicked) {
            this.value = isDaySelected()
        }
        addSource(isSundayClicked) {
            this.value = isDaySelected()
        }

    }

    private fun isDaySelected(): Boolean {
        return (isMondayClicked.value == true || isTuesdayClicked.value == true || isWednesdayClicked.value == true || isThursdayClicked.value == true || isFridayClicked.value == true || isSaturdayClicked.value == true || isSundayClicked.value == true)

    }

    fun onClickPickSchedule() {
        clickPickSchedule.value = Once(Unit)
    }
    fun onClickMonday() {
        isMondayClicked.value = !(isMondayClicked.value ?: false)
    }
    fun onClickTuesday() {
        isTuesdayClicked.value = !(isTuesdayClicked.value ?: false)
    }

    fun onClickWednesday() {
        isWednesdayClicked.value = !(isWednesdayClicked.value ?: false)
    }

    fun onClickThursday() {
        isThursdayClicked.value = !(isThursdayClicked.value ?: false)
    }

    fun onClickFriday() {
        isFridayClicked.value = !(isFridayClicked.value ?: false)
    }
    fun onClickSaturday() {
        isSaturdayClicked.value = !(isSaturdayClicked.value ?: false)
    }
    fun onClickSunday() {
        isSundayClicked.value = !(isSundayClicked.value ?: false)
    }
}