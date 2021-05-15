package com.goddoro.udc.views.upload

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.util.MultiPartUtil
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.util.HashMap


/**
 * created By DORO 2020/09/13
 */

class UploadEventViewModel (
    private val eventRepository: EventRepository
): ViewModel() {


    val curPoster: MutableLiveData<Uri> = MutableLiveData()
    val eventDetailImages : MutableLiveData<List<Uri>> = MutableLiveData()

    val title: MutableLiveData<String> = MutableLiveData()

    val location: MutableLiveData<String> = MutableLiveData()

    val description: MutableLiveData<String> = MutableLiveData()

    val date: MutableLiveData<String> = MutableLiveData()

    val type: MutableLiveData<String> = MutableLiveData()


    val clickPickPoster : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickImage: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickUploadButton: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickTypeDialog : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickCalendarDialog : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickSearchAddress : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPreview : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackStep : MutableLiveData<Once<Unit>> = MutableLiveData()

    val uploadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    init {


    }

    fun onClickPickImage() {
        clickPickImage.value = Once(Unit)
    }

    fun onClickPickPoster () {
        clickPickPoster.value = Once(Unit)
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickUploadButton() {

        viewModelScope.launch {
            kotlin.runCatching {
                eventRepository.uploadEvent(
                    name = title.value ?: "",
                    subTitle = "zxcv",
                    description = description.value,
                    date = date.value ?: "",
                    posterImg = curPoster.value!!,
                    eventType = "battle",
                    location = location.value!!
                )
            }.onSuccess {
                uploadCompleted.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }

    }

    fun onClickTypeDialog() {
        clickTypeDialog.value = Once(Unit)
    }
    fun onClickCalendarDialog() {
        clickCalendarDialog.value = Once(Unit)
    }

    fun onClickSearchAddress() {
        clickSearchAddress.value = Once(Unit)
    }

    fun onClickPreview() {
        clickPreview.value = Once(Unit)
    }

    fun onClickBackStep() {
        clickBackStep.value = Once(Unit)
    }
}