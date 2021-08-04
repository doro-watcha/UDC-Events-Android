package com.goddoro.udc.views.upload.danceClass

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.StrPatternChecker
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.ClassRepository
import kotlinx.coroutines.launch

class UploadClassViewModel(
    private val classRepository: ClassRepository
) : ViewModel() {

    val name: MutableLiveData<String> = MutableLiveData()
    val genre: MutableLiveData<Genre> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val youtubeUrl: MutableLiveData<String> = MutableLiveData()
    val academy: MutableLiveData<Academy> = MutableLiveData()
    val schedule : MutableLiveData<String> = MutableLiveData()
    val target: MutableLiveData<String> = MutableLiveData()

    val artistName: MutableLiveData<String> = MutableLiveData()
    val artistProfileImg: MutableLiveData<Uri> = MutableLiveData()
    val artistDescription: MutableLiveData<String> = MutableLiveData()

    var mainIndex = 0
    val detailImages: MutableLiveData<List<Uri>> = MutableLiveData()

    val clickGalleryButton: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickAcademy: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickGenre: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickLevel: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickPickSchedule : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickBackArrow: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickToImageStep: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickToSpecificStep: MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickArtistImage: MutableLiveData<Once<Unit>> = MutableLiveData()

    val isValidYoutubeUrl = MediatorLiveData<Boolean>().apply {

        addSource(youtubeUrl) {
            this.value = StrPatternChecker.YoutubeUrlTypeOk(it)
        }
    }


    val level: MutableLiveData<String> = MutableLiveData()


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

    fun onClickPickLevel() {
        clickPickLevel.value = Once(Unit)
    }
    fun onClickPickSchedule() {
        clickPickSchedule.value = Once(Unit)
    }

    fun onClickArtistImage() {
        clickArtistImage.value = Once(Unit)
    }

    fun onClickRegisterClass() {

        viewModelScope.launch {

//            kotlin.runCatching {
//                classRepository.
//            }
        }
    }
}