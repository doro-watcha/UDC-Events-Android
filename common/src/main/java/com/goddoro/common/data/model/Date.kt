package com.goddoro.common.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Date(

    val day: Int,

    val date: String,

    val dateInt : Int
) : Parcelable