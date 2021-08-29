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


    @GET("v1/class")
    suspend fun listClasses(
        @QueryMap parameters: HashMap<String, Any>
    ) : ApiResponse<ClassListResponse>

    @GET("v1/class/{id}")
    suspend fun getClass(
        @Path("id") id : Int
    ) : ApiResponse<ClassResponse>

    @POST("v1/class")
    @Multipart
    suspend fun registerClass(
        @PartMap parameters : HashMap<String, RequestBody>,
        @Part files : List<MultipartBody.Part>?
    ) : ApiResponse<Any>

    @GET("v1/popup/main")
    suspend fun getPopupClass() : ApiResponse<ClassResponse>


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