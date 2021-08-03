package com.goddoro.udc.views.upload.danceClass

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.common.Once
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.Genre

class UploadClassViewModel : ViewModel() {

    val name : MutableLiveData<String> = MutableLiveData()
    val genre : MutableLiveData<Genre> = MutableLiveData()
    val description : MutableLiveData<String> = MutableLiveData()
    val youtubeUrl : MutableLiveData<String> = MutableLiveData()
    val academy : MutableLiveData<Academy> = MutableLiveData()
    val target : MutableLiveData<String> = MutableLiveData()

    val mainPosterImage : MutableLiveData<Uri> = MutableLiveData()
    val detailImages : MutableLiveData<List<Uri>> = MutableLiveData()

    val clickGalleryButton : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickAcademy : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickGenre : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickToImageStep : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickToSpecificStep : MutableLiveData<Once<Unit>> = MutableLiveData()



    val level : MutableLiveData<String> = MutableLiveData()


    fun onClickGalleryButton() {
        clickGalleryButton.value = Once(Unit)
    }

    fun onClickPickAcademy() {
        clickPickAcademy.value = Once(Unit)
    }

    fun onClickPickGenre() {
        clickPickGenre.value = Once(Unit)
    }

    fun onClickToImage() {
        clickToImageStep.value = Once(Unit)
    }

    fun onClickToSpecific() {
        clickToSpecificStep.value = Once(Unit)
    }

    fun onClickBackArrow() {
        clickBackArrow.value = Once(Unit)
    }
}