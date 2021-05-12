package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/10/24
 */

@Parcelize
data class Notification (
    @SerializedName("id")
    val id : Int,

    @SerializedName("title")
    val title : String,

    @SerializedName("body")
    val body : String,

    @SerializedName("type")
    val type : String
) : Parcelable