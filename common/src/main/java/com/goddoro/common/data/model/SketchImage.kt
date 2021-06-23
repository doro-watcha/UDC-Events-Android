package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SketchImage(
    @SerializedName("id")
    val id: Int,

    @SerializedName("sketchImgUrl")
    val sketchImage: String
) : Parcelable