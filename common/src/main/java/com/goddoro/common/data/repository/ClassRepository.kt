package com.goddoro.common.data.repository

import android.net.Uri
import com.goddoro.common.data.model.DanceClass


/**
 * created By DORO 12/27/20
 */

interface ClassRepository {

    suspend fun listClasses (
        sort : String? = null,
        type : String? = null,
        day : Int? = null,
        genreId : Int? = null,
        academyId : Int? = null
    ) : List<DanceClass>

    suspend fun getClass (
         id : Int
    ) : DanceClass

    suspend fun registerClass (
        name : String,
        genreId : Int,
        academyId : Int,
        date : String,
        classDescription : String?= null,
        classYoutubeUrl: String,
        level : String,
        target : String,
        mainImage : Uri,
        subImages : List<Uri>?= null ,
        artistProfileImg : Uri,
        artistName : String,
        artistDescription : String ?= null ,
        artistInstagram : String ?= null




        ) : Any
}