package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 12/27/20
 */

@Parcelize
data class Artist (
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("team")
    val team : String? = null,

    @SerializedName("instagram")
    val instagramUrl : String? = null,

    @SerializedName("youtube")
    val youtubeUrl : String? = null,

    @SerializedName("location")
    val location : String? = null,

    @SerializedName("genre")
    val genre : String? = null,

    @SerializedName("profileImgUrl")
    val profileImgUrl : String? = null


) : Parcelable