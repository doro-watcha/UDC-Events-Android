package com.goddoro.common.data.repository

import android.net.Uri
import com.goddoro.common.data.model.Event
import io.reactivex.Completable


/**
 * created By DORO 2020/10/10
 */

interface EventRepository{


    suspend fun listEventsBySort(
        sort : String
    ) : List<Event>

    suspend fun listEventsByStatus(
        status : String
    ) : List<Event>

    suspend fun getEvent (
        eventId : Int
    ) : Event

    suspend fun uploadEvent(
        name : String,
        subTitle : String,
        description : String,
        date : String,
        location : String,
        eventType : String,
        posterImg : Uri,
        sketchImgs : List<Uri>
    ) : Any

    suspend fun updateStatus(
        eventId : Int,
        status : String
    ) : Any

}