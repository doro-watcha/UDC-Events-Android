package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import com.goddoro.common.data.api.response.ApiResponse
interface SearchAPI {


    @GET("v1/search")
    suspend fun listRecommendations(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<RecommendationListResponse>

    @GET("v1/event/{id}")
    suspend fun listSearchEvent(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<SearchEventListResponse>
}

@Parcelize
data class RecommendationListResponse(
    @SerializedName("recommendations")
    val recommendations : List<String>
) : Parcelable

@Parcelize
data class SearchEventListResponse(
    @SerializedName("events")
    val events : List<Event>
) : Parcelable