package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.EventAPI
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.util.MultiPartUtil
import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * created By DORO 2020/10/10
 */

class EventRepositoryImpl ( val api : EventAPI, private val multiPartUtil: MultiPartUtil) : EventRepository {

    override suspend fun listEventsBySort(sort: String): List<Event> {

        val params = hashMapOf(
            "sort" to sort
        ).filterValueNotNull()

        return api.listEventsBySOrt(params).unWrapData().events
    }
    override suspend fun getEvent( eventId : Int ): Event {

        return api.getEvent(eventId).unWrapData().event
    }

    override suspend fun listEventsByStatus(status: String): List<Event> {

        val params = hashMapOf(
            "status" to status
        ).filterValueNotNull()

        return api.listEventsBySOrt(params).unWrapData().events
    }


    override suspend fun uploadEvent(
        name: String,
        subTitle: String,
        description: String,
        date: String,
        location: String,
        eventType: String,
        posterImg: Uri,
        sketchImgs : List<Uri>,
        longitude : Double,
        latitude : Double
    ): Any {


        val params : HashMap<String,RequestBody> = hashMapOf()

        val files = mutableListOf<MultipartBody.Part>()

        params["name"] = multiPartUtil.stringToPart(name)
        params["subtitle"] = multiPartUtil.stringToPart(subTitle)
        params["description"] = multiPartUtil.stringToPart(description ?: "")
        params["date"] = multiPartUtil.stringToPart(date)
        params["location"] = multiPartUtil.stringToPart(location)
        params["eventType"] = multiPartUtil.stringToPart(eventType)
        params["longitude"] = multiPartUtil.stringToPart(longitude.toString())
        params["latitude"] = multiPartUtil.stringToPart(latitude.toString())

        posterImg.let { files.add(multiPartUtil.uriToPart("posterImg", it)) }

        sketchImgs.forEach {
            files.add(multiPartUtil.uriToPart("sketchImgs",it))
        }


        return api.uploadEvent(params, files).unWrapCompletable()
    }

    override suspend fun updateStatus(eventId: Int, status: String): Any {

        val params = hashMapOf(
            "status" to status
        ).filterValueNotNull()

        return api.updateEvent(eventId,params).unWrapCompletable()
    }

}