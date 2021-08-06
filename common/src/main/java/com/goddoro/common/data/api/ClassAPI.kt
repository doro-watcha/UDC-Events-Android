package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.api.response.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


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

    @POST("class")
    @Multipart
    suspend fun registerClass(
        @PartMap parameters : HashMap<String, RequestBody>,
        @Part files : List<MultipartBody.Part>?
    ) : ApiResponse<Any>


}

@Parcelize
data class ClassListResponse(
    @SerializedName("classes")
    val classes : List<DanceClass>
) : Parcelable

@Parcelize
data class ClassResponse(
    @SerializedName("class")
    val danceClass : DanceClass
) : Parcelable