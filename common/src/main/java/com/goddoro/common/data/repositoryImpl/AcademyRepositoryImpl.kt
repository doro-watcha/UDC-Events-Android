package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.AcademyAPI
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.repository.AcademyRepository
import com.goddoro.common.util.MultiPartUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AcademyRepositoryImpl ( private val api : AcademyAPI, private val multiPartUtil: MultiPartUtil) : AcademyRepository{

    override suspend fun listAcademies(): List<Academy> {
        return api.listAcademies().data?.academies ?: listOf()
    }


    override suspend fun registerAcademy(name: String, location: String, logoImage: Uri?): Any {
        val params : HashMap<String, RequestBody> = hashMapOf()

        val files = mutableListOf<MultipartBody.Part>()

        params["name"] = multiPartUtil.stringToPart(name)
        params["location"] = multiPartUtil.stringToPart(location)

        logoImage?.let { files.add(multiPartUtil.uriToPart("logoImg", it)) }

        return api.registerAcademy(params, files).unWrapCompletable()
    }
}