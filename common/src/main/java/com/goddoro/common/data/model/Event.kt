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

    @SerializedName("posterImgUrl")
    val posterUrl : String,

    @SerializedName("eventType")
    val eventType : String? = "battle",


    @SerializedName("location")
    val location : String? = null,

    @SerializedName("sketchImgs")
    val sketchImages : List<SketchImage>? = null,

    @SerializedName("createdAt")
    val createdAt : String,

    @SerializedName("status")
    val status : String,

    @SerializedName("subtitle")
    val subtitle : String?= "많은 관심 부탁드립니다",

    @SerializedName("description")
    val description : String? = "많은 관심 부탁드립니다",

    @SerializedName("longitude")
    val longitude : Double? = 0.0,

    @SerializedName("latitude")
    val latitude : Double? = 0.0,

    @SerializedName("isLike")
    val isLike : Boolean? = false
) : Parcelable, KoinComponent{

    fun getFormedDate() : String {

        val dateUtil = get<DateUtil>()
        return dateUtil.changeDateFormat(createdAt)

    }

    fun getType() : String {
        return when (eventType) {

            "battle" -> "배틀"
            "performance" -> "퍼포먼스"
            "party" -> "파티"
            else -> "스트릿 행사"
        }

    }
}