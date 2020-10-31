package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/08/16
 */

@Parcelize
data class Event (
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String?,

    val blurredImage : String? = "https://cdn.beatflo.co/video/mrbmraxzm4h_1584026271523.jpg",
    val imageId : Int? = 0,

    @SerializedName("posterUrl")
    val posterUrl : String,


    @SerializedName("location")
    val location : String? = null
) : Parcelable