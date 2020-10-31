package com.goddoro.common.data.service

import android.os.Parcelable
import com.goddoro.common.data.model.Event
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * created By DORO 2020/09/26
 */

interface EventService {

    /**
     * Upload Event
     */
    @POST("event")
    @Multipart
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Part posterFile: MultipartBody.Part,
        @PartMap params: HashMap<String, RequestBody>
    )

    /**
     * Event list
     */
    @GET("event")
    suspend fun listEvent(
        @QueryMap params : HashMap<String, Any>
    ) : EventListResponse
}

@Parcelize
data class EventListResponse(

    @SerializedName("events")
    val events : List<Event>

) : Parcelable