package com.goddoro.common.data.model

import android.os.Parcelable
import com.goddoro.common.util.DateUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.koin.core.KoinComponent
import org.koin.core.get


/**
 * created By DORO 2020/08/16
 */

@Parcelize
data class Event (
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String?,

    val blurredImage : String?,

    val imageId : Int? = 0,

    @SerializedName("posterImgUrl")
    val posterUrl : String,


    @SerializedName("location")
    val location : String? = null,

    @SerializedName("sketchImgs")
    val sketchImages : List<String>? = null,

    @SerializedName("createdAt")
    val createdAt : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("subtitle")
    val subtitle : String?= "많은 관심 부탁드립니다",

    @SerializedName("description")
    val description : String? = "많은 관심 부탁드립니다"
) : Parcelable, KoinComponent{

    fun getFormedDate() : String {

        val dateUtil = get<DateUtil>()
        return dateUtil.changeDateFormat(createdAt)

    }
}