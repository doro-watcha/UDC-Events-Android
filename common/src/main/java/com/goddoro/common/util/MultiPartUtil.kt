package com.goddoro.common.util

import android.net.Uri
import com.goddoro.common.common.debugE
import com.goddoro.common.util.UriUtil
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


/**
 * created By DORO 2020/09/26
 */

class MultiPartUtil(private val fileUriUtil: UriUtil) {

    fun stringToPart(text: String): RequestBody {
        return text.toRequestBody(MultipartBody.FORM)
    }


    fun uriToPart(name: String, uri: Uri): MultipartBody.Part {

        val mime: MediaType = "video/mp4".toMediaType()

        val file = File(
            try {
                fileUriUtil.getPathFromUri(uri) ?: ""
            } catch (t: Throwable) {
                uri.path ?: ""
            }
        )

        return MultipartBody.Part.createFormData(
            name,
            file.name,
            file.asRequestBody(mime)
        )
    }

    fun pathToPart(name: String, path: String): MultipartBody.Part {
        val file = File(path)
        debugE(file.exists())

        return MultipartBody.Part.createFormData(
            name,
            file.name,
            RequestBody.create(fileUriUtil.getTypeOfFile(path), file)
        )
    }
}