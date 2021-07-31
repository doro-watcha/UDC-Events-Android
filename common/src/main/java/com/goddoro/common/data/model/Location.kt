package com.goddoro.common.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(

    val address: String,

    val x: Double,

    val y: Double
) : Parcelable