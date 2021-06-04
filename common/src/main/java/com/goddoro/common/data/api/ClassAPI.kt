package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.api.response.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


/**
 * created By DORO 12/27/20
 */

interface ClassAPI {


    @GET("class")
    suspend fun listClasses(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<ClassListResponse>

    @GET("class/{id}")
    suspend fun getClass(
        @Path("id") id : Int
    ) : ApiResponse<ClassResponse>
}

@Parcelize
data class ClassListResponse(
    @SerializedName("classInfo")
    val classes : List<DanceClass>
) : Parcelable

@Parcelize
data class ClassResponse(
    @SerializedName("class")
    val danceClass : DanceClass
) : Parcelable