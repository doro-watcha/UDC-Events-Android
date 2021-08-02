package com.goddoro.udc.views.upload.danceClass

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once

class UploadClassViewModel : ViewModel() {

    val name : MutableLiveData<String> = MutableLiveData()
    val genre : MutableLiveData<String> = MutableLiveData()
    val description : MutableLiveData<String> = MutableLiveData()
    val youtubeUrl : MutableLiveData<String> = MutableLiveData()
    val location : MutableLiveData<String> = MutableLiveData()
    val target : MutableLiveData<String> = MutableLiveData()

    val mainPosterImage : MutableLiveData<Uri> = MutableLiveData()
    val detailImages : MutableLiveData<List<Uri>> = MutableLiveData()

    val clickGalleryButton : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickAcademy : MutableLiveData<Once<Unit>> = MutableLiveData()



    val level : MutableLiveData<String> = MutableLiveData()


    fun onClickGalleryButton() {
        clickGalleryButton.value = Once(Unit)
    }

    fun onClickPickAcademy() {
        clickPickAcademy.value = Once(Unit)
    }
}