package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import com.goddoro.common.data.api.UserAPI
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.UserRepository
import com.goddoro.common.util.MultiPartUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * created By DORO 2020/10/10
 */

class UserRepositoryImpl ( val api : UserAPI , private val multiPartUtil: MultiPartUtil): UserRepository {

    override suspend fun getUser(userId: Int): User {

        return api.getUser(userId).unWrapData().user
    }

    override suspend fun updateProfile(profileImage: Uri): Any {


        val params : HashMap<String, RequestBody> = hashMapOf()

        val files = mutableListOf<MultipartBody.Part>()

        profileImage.let { files.add(multiPartUtil.uriToPart("profileImg", it)) }

        return api.updateProfile(files).unWrapCompletable()
    }

    override suspend fun updateUser() {

    }


}

