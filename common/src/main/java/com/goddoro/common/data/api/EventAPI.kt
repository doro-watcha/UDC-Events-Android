package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.*
import com.goddoro.common.data.api.response.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * created By DORO 2020/10/10
 */

interface EventAPI {

    @GET("v1/event")
    suspend fun listEventsBySOrt(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<EventListResponse>

    @GET("v1/event/{id}")
    suspend fun getEvent(
        @Path("id") eventId : Int
    ) : ApiResponse<EventResponse>

    @POST("v1/event")
    @Multipart
    suspend fun uploadEvent(
        @PartMap parameters : HashMap<String,RequestBody>,
        @Part files : List<MultipartBody.Part>?
    ) : ApiResponse<Any>

    @PATCH("v1/event/{id}")
    @FormUrlEncoded
    suspend fun updateEvent(
        @Path("id") eventId : Int,
        @FieldMap parameters : HashMap<String,Any>
    ) : ApiResponse<Any>

}

@Parcelize
data class EventListResponse(
    @SerializedName("events")
    val events : List<Event>
) : Parcelable

@Parcelize
data class EventResponse(
    @SerializedName("event")
    val event : Event
) : Parcelable