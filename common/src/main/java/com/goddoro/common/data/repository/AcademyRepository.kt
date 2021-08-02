package com.goddoro.common.data.repository

import android.net.Uri
import com.goddoro.common.data.model.Academy

interface AcademyRepository {


    suspend fun listAcademies() : List<Academy>

    suspend fun registerAcademy (
        name : String,
        location : String,
        logoImage : Uri?= null
    ) : Any
}