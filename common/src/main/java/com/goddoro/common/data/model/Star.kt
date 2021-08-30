package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Star(
    @SerializedName("id")
    val id: Int,

    @SerializedName("point")
    var point: Float
) : Parcelable