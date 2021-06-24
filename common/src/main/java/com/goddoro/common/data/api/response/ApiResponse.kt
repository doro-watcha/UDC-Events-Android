package com.goddoro.common.data.api.response

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import com.goddoro.common.data.api.response.ResponseCode
import com.goddoro.common.data.api.response.UnWrappingDataException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.Completable
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize


/**
 * created By DORO 2020/10/03
 */

@Parcelize
data class ApiResponse<out T>(
    @SerializedName("success")
    @Expose
    val isSuccess: Boolean,

    @SerializedName("message")
    @Expose
    val message: String?,

    @SerializedName("ecode")
    @Expose
    val errorCode: Int?,

    @SerializedName("data")
    @Expose
    val data: @RawValue T?
) : Parcelable {

    fun unWrapData() = run {
        when (isSuccess) {
            true -> data!!

            else -> throw UnWrappingDataException(
                errorCode ?: ResponseCode.UNKNOWN.code,
                "${errorCode} : ${message}"
            )
        }
    }

    fun unWrapCompletable(): Completable =

        when (isSuccess) {
            true -> Completable.complete()

            else -> throw UnWrappingDataException(

                errorCode ?: ResponseCode.UNKNOWN.code, "${errorCode} : ${message}"
            )

        }

}



