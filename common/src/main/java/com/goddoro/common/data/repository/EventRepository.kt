package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Event


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

}