package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/10/17
 */

@Parcelize
data class Tag (
    @SerializedName("id")
    val id : Int,

    @SerializedName("content")
    val content : String,

    @SerializedName("type")
    val type : String

) : Parcelable