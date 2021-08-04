package com.goddoro.udc.views.search.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.model.Target

class FilterViewModel : ViewModel() {

    val genres : MutableLiveData<List<Genre>> = MutableLiveData()

    val targets : MutableLiveData<List<Target>> = MutableLiveData()

    init {

        genres.value = listOf(
            Genre(0,"비보잉"),
            Genre(1,"걸스힙합"),
            Genre(2,"팝핀")
        )

        targets.value = listOf(
            Target(0,"초보"),
            Target(1,"중수"),
            Target(2,"고수")
        )
    }
}