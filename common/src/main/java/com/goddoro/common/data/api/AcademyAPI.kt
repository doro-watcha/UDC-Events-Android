package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.api.response.ApiResponse
import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.Result
import com.goddoro.common.data.model.Status
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AcademyAPI {


    @GET("academy")
    suspend fun listAcademies(

    ) : ApiResponse<AcademyListResponse>

    @POST("academy")
    @Multipart
    suspend fun registerAcademy(
        @PartMap parameters : HashMap<String, RequestBody>,
        @Part files : List<MultipartBody.Part>?
    ) : ApiResponse<Any>

}

@Parcelize
data class AcademyListResponse(
    @SerializedName("academies")
    val academies : List<Academy>
) : Parcelable