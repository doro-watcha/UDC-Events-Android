package com.goddoro.udc.views.classShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Artist
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.ClassRepository

class GenreClassViewModel(
    val genre : Genre,
    private val classRepository: ClassRepository
) : ViewModel() {

    val genreClasses: MutableLiveData<List<DanceClass>> = MutableLiveData()

    init {

        genreClasses.value = listOf(

            DanceClass(
                0,
                "zxcv",
                Artist(0, "zxcv", "zxcv", "zxcv"),
                "zxcv",
                "zxcv",
                "zxcv",
                "zxcv",
                false,
                "zxcv"
            ),
            DanceClass(
                0,
                "zxcv",
                Artist(0, "zxcv", "zxcv", "zxcv"),
                "zxcv",
                "zxcv",
                "zxcv",
                "zxcv",
                false,
                "zxcv"
            ),
            DanceClass(
                0,
                "zxcv",
                Artist(0, "zxcv", "zxcv", "zxcv"),
                "zxcv",
                "zxcv",
                "zxcv",
                "zxcv",
                false,
                "zxcv"
            ),
            DanceClass(
                0,
                "zxcv",
                Artist(0, "zxcv", "zxcv", "zxcv"),
                "zxcv",
                "zxcv",
                "zxcv",
                "zxcv",
                false,
                "zxcv"
            ),
            DanceClass(
                0,
                "zxcv",
                Artist(0, "zxcv", "zxcv", "zxcv"),
                "zxcv",
                "zxcv",
                "zxcv",
                "zxcv",
                false,
                "zxcv"
            )
        )
    }
}