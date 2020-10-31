package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/10/24
 */

@Parcelize
data class DanceClass (
    @SerializedName("id")
    val id : Int,

    @SerializedName("posterImgUrl")
    val posterImgUrl : String,

    @SerializedName("author")
    val author : User? = null,

    @SerializedName("description")
    val description :String? = null
) : Parcelable