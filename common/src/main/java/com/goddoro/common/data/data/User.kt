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

    @SerializedName("avatarUrl")
    val avatarUrl: String?,

    @SerializedName("coverUrl")
    val coverUrl: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("loginType")
    val loginType: LoginType,

    @SerializedName("email")
    val email: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("credentials")
    val credentials: String?,

    @SerializedName("role")
    val role: String?
) : Parcelable
