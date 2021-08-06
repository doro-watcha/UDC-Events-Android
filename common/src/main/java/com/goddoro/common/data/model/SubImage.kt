package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize

data class SubImage(
    @SerializedName("id")
    val id: Int,

    @SerializedName("subImgUrl")
    val subImgUrl: String
) : Parcelable