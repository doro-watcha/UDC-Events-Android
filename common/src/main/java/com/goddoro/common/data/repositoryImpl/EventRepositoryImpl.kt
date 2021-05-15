package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.EventAPI
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.util.MultiPartUtil
import okhttp3.MultipartBody


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
        description: String?,
        date: String,
        location: String,
        eventType: String,
        posterImg: Uri
    ): Any {


        val params = hashMapOf(
            "name" to name,
            "subtitle" to subTitle,
            "description" to description,
            "date" to date,
            "location" to location,
            "eventType" to eventType
        ).filterValueNotNull()


        val files = mutableListOf<MultipartBody.Part>()

        posterImg.let { files.add(multiPartUtil.uriToPart("posterImg", it)) }


        return api.uploadEvent(params).unWrapCompletable()
    }

}