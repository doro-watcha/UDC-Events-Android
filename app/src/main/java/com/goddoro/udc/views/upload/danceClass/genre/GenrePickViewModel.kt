package com.goddoro.udc.views.upload.danceClass.genre

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.GenreRepository
import kotlinx.coroutines.launch

class GenrePickViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {

    val genres : MutableLiveData<List<Genre>> = MutableLiveData()

    val selectedGenre : MutableLiveData<Genre> = MutableLiveData()

    val errorInvoked : MutableLiveData<Throwable> = MutableLiveData()

    val isGenreSelected = MediatorLiveData<Boolean>().apply {

        addSource(selectedGenre){
            this.value = it != null
        }
    }
    init {
        listGenres()

    }

    fun listGenres() {
//
//        genres.value = listOf(
//            Genre(0,"비보잉"),
//            Genre(1,"팝핀"),
//            Genre(2,"락킹")
//        )
//
        viewModelScope.launch {

            kotlin.runCatching {
                genreRepository.listGenre()
            }.onSuccess {
                genres.value = it
            }.onFailure {
                errorInvoked.value = it
            }

        }
    }
}