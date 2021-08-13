package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 2020/08/16
 */

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String?,

    @SerializedName("loginId")
    val loginId : String,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("nickname")
    val nickname : String? = null,

    @SerializedName("loginType")
    val loginType: String,

    @SerializedName("isUDC")
    val isUDC : Boolean? = null ,

    @SerializedName("isAdmin")
    val isAdmin : Boolean? = false,

    @SerializedName("location")
    val location : String? = null,

    @SerializedName("profileImgUrl")
    val avatarUrl : String? = null,

    @SerializedName("followArtists")
    val followArtists : List<Tag>? = null,

    @SerializedName("genres")
    val genres : List<String>? = null

) : Parcelable
