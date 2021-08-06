package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 2020/10/24
 */

@Parcelize
data class DanceClass (
    @SerializedName("id")
    val id : Int,

    @SerializedName("mainImgUrl")
    val mainImgUrl : String,

    @SerializedName("description")
    val description :String? = null,


    @SerializedName("youtubeUrl")
    val youtubeUrl : String?= null,


    @SerializedName("isMainClass")
    val isMainClass : Boolean,

    @SerializedName("name")
    val title : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("date")
    val date : String,

    @SerializedName("startTime")
    val startTime : String,

    @SerializedName("level")
    val level : String,

    @SerializedName("target")
    val target : String,

    @SerializedName("artistName")
    val artistName : String,

    @SerializedName("artistProfileImgUrl")
    val artistProfileImgUrl : String,

    @SerializedName("artistDescription")
    val artistDescription : String? = null,

    @SerializedName("artistInstagram")
    val artistInstagram : String? = null,

    @SerializedName("academy")
    val academy: Academy,

    @SerializedName("genre")
    val genre : Genre,

    @SerializedName("subImgs")
    val subImgs : List<SubImage>,


    val temporaryImage : Int ? = 0

) : Parcelable {

}