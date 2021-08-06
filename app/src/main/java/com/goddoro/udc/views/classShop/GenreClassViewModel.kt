package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.data.model.Artist
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.ClassRepository
import kotlinx.coroutines.launch

class GenreClassViewModel(
    val genre : Genre,
    private val classRepository: ClassRepository
) : ViewModel() {

    val genreClasses: MutableLiveData<List<DanceClass>> = MutableLiveData()

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    init {
        listGenreClasses()
    }

    private fun listGenreClasses() {

        viewModelScope.launch {

            kotlin.runCatching {
                classRepository.listClasses(genreId = genre.id)
            }.onSuccess {
                genreClasses.value = it
            }.onFailure {
                errorInvoked.value = it
            }
        }
    }
}