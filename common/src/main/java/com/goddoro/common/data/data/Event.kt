package com.goddoro.common.data.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/08/16
 */

@Parcelize
data class Event (
    val id : Int,
    val blurredImage : String? = "https://cdn.beatflo.co/video/mrbmraxzm4h_1584026271523.jpg",
    val imageId : Int? = 0,
    val imageUrl : String? = "https://cdn.beatflo.co/video/mrbmraxzm4h_1584026271523.jpg",
    val location : String? = null
) : Parcelable