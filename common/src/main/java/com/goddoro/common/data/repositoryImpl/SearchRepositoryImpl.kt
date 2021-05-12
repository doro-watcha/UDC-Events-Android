package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.SearchAPI
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.repository.SearchRepository


/**
 * created By DORO 11/28/20
 */

class SearchRepositoryImpl ( val api : SearchAPI) : SearchRepository {
    override suspend fun listSearchRecommendations( query : String): List<String> {
        val params = hashMapOf(
          "query" to query
        ).filterValueNotNull()

        return api.listRecommendations(params).unWrapData().recommendations
    }

    override suspend fun listSearchEvent( query : String ): List<Event> {
        val params = hashMapOf(
            "query" to query
        ).filterValueNotNull()

        return api.listSearchEvent(params).unWrapData().events
    }


}