package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.ClassAPI
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository
import com.goddoro.common.util.MultiPartUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * created By DORO 12/27/20
 */

class ClassRepositoryImpl ( val api : ClassAPI, private val multiPartUtil: MultiPartUtil) : ClassRepository {
    override suspend fun listClasses( sort : String?, type : String?, day: Int?, genreId : Int?, academyId : Int?): List<DanceClass> {

        val params = hashMapOf(
            "sort" to sort,
            "classType" to type,
            "day" to day,
            "genreId" to genreId,
            "academyId" to academyId
        ).filterValueNotNull()

        return api.listClasses(params).unWrapData().classes
    }

    override suspend fun getClass(id: Int): DanceClass {

        return api.getClass(id).unWrapData().danceClass

    }


    override suspend fun registerClass(
        name: String,
        genreId: Int,
        academyId: Int,
        date: String,
        classDescription: String?,
        classYoutubeUrl: String,
        level: String,
        target: String,
        mainImage: Uri,
        subImages: List<Uri>?,
        artistProfileImg: Uri,
        artistName: String,
        artistDescription: String?,
        artistInstagram: String?
    ): Any {
        val params : HashMap<String, RequestBody> = hashMapOf()

        val files = mutableListOf<MultipartBody.Part>()

        params["name"] = multiPartUtil.stringToPart(name)
        params["genreId"] = multiPartUtil.stringToPart(genreId.toString())
        params["academyId"] = multiPartUtil.stringToPart(academyId.toString())
        params["date"] = multiPartUtil.stringToPart(date)
        params["classDescription"] = multiPartUtil.stringToPart(classDescription ?: "")
        params["level"] = multiPartUtil.stringToPart(level)
        params["target"] = multiPartUtil.stringToPart(target)
        params["artistName"] = multiPartUtil.stringToPart(artistName)
        params["artistDescription"] = multiPartUtil.stringToPart(artistDescription ?: "")
        params["artistInstagram"] = multiPartUtil.stringToPart(artistInstagram ?: "")

        mainImage.let { files.add(multiPartUtil.uriToPart("maingImage", it)) }
        artistProfileImg.let { files.add(multiPartUtil.uriToPart("artistProfileImg",it))}

        subImages?.forEach {
            files.add(multiPartUtil.uriToPart("sketchImgs",it))
        }

        return api.registerClass(params,files).unWrapCompletable()
    }
}