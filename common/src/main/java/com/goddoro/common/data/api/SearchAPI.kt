package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface SearchAPI {


    @GET("search")
    suspend fun listRecommendations(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<RecommendationListResponse>

    @GET("event/{id}")
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