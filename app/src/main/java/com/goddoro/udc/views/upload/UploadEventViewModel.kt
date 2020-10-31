package com.goddoro.udc.views.upload

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.util.MultiPartUtil
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.util.HashMap


/**
 * created By DORO 2020/09/13
 */

class UploadEventViewModel (
    private val multiPartUtil: MultiPartUtil
): ViewModel() {


    val curPoster: MutableLiveData<Uri> = MutableLiveData()

    val title: MutableLiveData<String> = MutableLiveData()

    val location: MutableLiveData<String> = MutableLiveData()

    val description: MutableLiveData<String> = MutableLiveData()

    val date: MutableLiveData<String> = MutableLiveData()

    val type: MutableLiveData<String> = MutableLiveData()


    val clickPickImage: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickUploadButton: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickTypeDialog : MutableLiveData<Once<Unit>> = MutableLiveData()

    val uploadCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()

    fun onClickPickImage() {
        clickPickImage.value = Once(Unit)
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }

    fun onClickUploadButton() {

        viewModelScope.launch {
            kotlin.runCatching {

                val params: HashMap<String, RequestBody> = hashMapOf()

                params["name"] = multiPartUtil.stringToPart(title.value ?: "")
                params["location"] = multiPartUtil.stringToPart(location.value ?: "")
                params["date"] = multiPartUtil.stringToPart(date.value ?: "")
                params["eventType"] = multiPartUtil.stringToPart("party")

//                NetworkClient.eventService.createEvent(
//                    "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJnb2Rkb3JvQG5hdmVyLmNvbSIsImlhdCI6MTYwMTExMTkyMiwiZXhwIjoxNjA2Mjk1OTIyfQ.8yactGVIZquamv8CYpKEXOD3C3mD1nnAVypxk9_yQ7A"
//                            ,MultiPartUtil.uriToPart(
//                        "posterImg", curPoster.value!!),
//                    params
//                )
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
}