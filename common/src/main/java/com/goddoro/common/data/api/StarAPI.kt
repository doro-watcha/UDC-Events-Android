package com.goddoro.common.data.api

import android.os.Parcelable
import com.goddoro.common.data.api.response.ApiResponse
import com.goddoro.common.data.model.Star
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import okhttp3.RequestBody
import retrofit2.http.*

interface StarAPI {

    @POST("rating")
    @FormUrlEncoded
    suspend fun createStar (
        @FieldMap parameters : HashMap<String, Any>,
    ) : ApiResponse<Any>

    @DELETE("rating/{id}")
    suspend fun deleteStar(
        @Path("id") id : Int
    ) : ApiResponse<Any>


}

@Parcelize
data class StarResponse(

    @SerializedName("star")
    val star : Star
) : Parcelable

