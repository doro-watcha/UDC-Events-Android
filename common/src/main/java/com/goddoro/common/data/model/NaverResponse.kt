package com.goddoro.common.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * created By DORO 4/25/21
 */


@Parcelize
data class Status(
    @SerializedName("code")
    val code : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("message")
    val message : String

): Parcelable

@Parcelize
data class Code (
    @SerializedName("id")
    val id : String,

    @SerializedName("type")
    val type : String,

    @SerializedName("mappingId")
    val mappingId : String
) : Parcelable

@Parcelize
data class Area(
    @SerializedName("name")
    val name : String
) : Parcelable

@Parcelize
data class Region (
    @SerializedName("area0")
    val area0 : Area,

    @SerializedName("area1")
    val area1 : Area,

    @SerializedName("area2")
    val area2 : Area,

    @SerializedName("area3")
    val area3 : Area,

    @SerializedName("area4")
    val area4 : Area
) : Parcelable

@Parcelize
data class Land (

    @SerializedName("type")
    val type : String,

    @SerializedName("number1")
    val number1 : String,

    @SerializedName("number2")
    val number2 : String,

    @SerializedName("name")
    val name : String
) : Parcelable

@Parcelize
data class Result (
    @SerializedName("name")
    val name : String,

    @SerializedName("code")
    val code : Code,

    @SerializedName("region")
    val region : Region,

    @SerializedName("land")
    val land : Land

) : Parcelable


@Parcelize
data class Meta (
    @SerializedName("totalCount")
    val totalCount : Int,

    @SerializedName("page")
    val page : Int,

    @SerializedName("count")
    val count : Int
) : Parcelable

@Parcelize
data class AddressElement(

    @SerializedName("types")
    val types : List<String>,

    @SerializedName("longName")
    val longName : String,

    @SerializedName("shortName")
    val shortName : String,

    @SerializedName("code")
    val code : String
) : Parcelable


@Parcelize
data class Address (

    @SerializedName("roadAddress")
    val roadAddress : String? = null ,

    @SerializedName("jibunAddress")
    val jibunAddress : String? = null,

    @SerializedName("englishAddress")
    val englishAddress : String? = null,

    @SerializedName("addresses")
    val addresses : List<AddressElement>,

    @SerializedName("x")
    val x : String,

    @SerializedName("y")
    val y : String,

    @SerializedName("distance")
    val distance : Float
) : Parcelable
