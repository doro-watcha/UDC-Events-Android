package com.goddoro.udc.views.upload

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import javax.inject.Inject


/**
 * created By DORO 2020/09/13
 */

class UploadEventViewModel @Inject constructor() : ViewModel() {


    val curPoster : MutableLiveData<Uri> = MutableLiveData()

    val title : MutableLiveData<String> = MutableLiveData()

    val description : MutableLiveData<String> = MutableLiveData()

    val startDate : MutableLiveData<String> = MutableLiveData()

    val endDate : MutableLiveData<String> = MutableLiveData()

    val type : MutableLiveData<String> = MutableLiveData()




    val clickPickImage : MutableLiveData<Once<Unit>> = MutableLiveData()


    fun onClickPickImage () {
        clickPickImage.value = Once(Unit)
    }
}