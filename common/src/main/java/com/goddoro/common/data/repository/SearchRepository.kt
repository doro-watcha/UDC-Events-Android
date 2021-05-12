package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Event

interface SearchRepository {

    suspend fun listSearchRecommendations (
        query : String
    ) : List<String>

    suspend fun listSearchEvent (
        query : String
    ) : List<Event>
}