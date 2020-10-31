package com.goddoro.common.data.model

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

    @SerializedName("location")
    val location : String? = null,

    @SerializedName("avatarUrl")
    val avatarUrl : String? = null,

    @SerializedName("followArtists")
    val followArtists : List<Tag>? = null,

    @SerializedName("genres")
    val genres : List<String>? = null

) : Parcelable
