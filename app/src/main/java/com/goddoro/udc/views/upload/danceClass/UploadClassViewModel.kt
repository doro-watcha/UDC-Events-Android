package com.goddoro.udc.views.upload.danceClass

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UploadClassViewModel : ViewModel() {

    val name : MutableLiveData<String> = MutableLiveData()
    val genre : MutableLiveData<String> = MutableLiveData()
    val description : MutableLiveData<String> = MutableLiveData()
    val youtubeUrl : MutableLiveData<String> = MutableLiveData()
    val location : MutableLiveData<String> = MutableLiveData()

    val mainPosterImage : MutableLiveData<Uri> = MutableLiveData()
    val detailImages : MutableLiveData<List<Uri>> = MutableLiveData()



    val level : MutableLiveData<String> = MutableLiveData()
}