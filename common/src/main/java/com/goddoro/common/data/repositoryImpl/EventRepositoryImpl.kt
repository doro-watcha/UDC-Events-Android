package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.EventAPI
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.EventRepository


/**
 * created By DORO 2020/10/10
 */

class EventRepositoryImpl ( val api : EventAPI) : EventRepository {

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

}