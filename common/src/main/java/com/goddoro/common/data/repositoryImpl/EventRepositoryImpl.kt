package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.EventAPI
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository


/**
 * created By DORO 2020/10/10
 */

class EventRepositoryImpl ( val api : EventAPI) : EventRepository {
    override suspend fun getUpcomingEventList(): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewEventList(): List<Event> {

        val params = hashMapOf(
            "zxcv" to null
        ).filterValueNotNull()
        return api.getEventList(params).unWrapData().events

    }

    override suspend fun getHotEventList(): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getMainEventList(): List<Event> {
        val params = hashMapOf(
            "isMainEvent" to true
        ).filterValueNotNull()

        return api.getEventList(params).unWrapData().events
    }

    override suspend fun getEvent( eventId : Int ): Event {

        return api.getEvent(eventId).unWrapData().event
    }
}