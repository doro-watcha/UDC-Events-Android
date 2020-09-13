package com.goddoro.common.data.data

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/08/16
 */

@Parcelize
data class User(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerializedName("email")
    val email: String?,

    @SerializedName("username")
    val username: String,

    @SerializedName("loginType")
    val loginType: LoginType,

    @SerializedName("isUDC")
    val isUDC : Boolean,

    @SerializedName("avatarUrl")
    val avatarUrl : String? = null

) : Parcelable
