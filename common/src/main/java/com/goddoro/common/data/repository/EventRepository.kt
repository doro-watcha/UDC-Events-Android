package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Event


/**
 * created By DORO 2020/10/10
 */

interface EventRepository{


    suspend fun getUpcomingEventList () : List<Event>

    suspend fun getNewEventList () : List<Event>

    suspend fun getHotEventList () : List<Event>

    suspend fun getMainEventList() : List<Event>

    suspend fun getEvent (
        eventId : Int
    ) : Event

}