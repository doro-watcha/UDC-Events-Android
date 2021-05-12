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

    @SerializedName("artist")
    val artist : Artist,

    @SerializedName("description")
    val description :String? = null,

    @SerializedName("career")
    val career : String? = null,

    @SerializedName("youtubeUrl")
    val youtubeUrl : String?= null,

    @SerializedName("classType")
    val classType : String,

    @SerializedName("isMainClass")
    val isMainClass : Boolean,

    @SerializedName("name")
    val title : String

) : Parcelable