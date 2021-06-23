package com.goddoro.common.data.repository

import android.net.Uri
import com.goddoro.common.data.model.User


/**
 * created By DORO 2020/10/10
 */

interface UserRepository {

    suspend fun getUser (
        userId : Int
    ) : User

    suspend fun updateUser(

    )

    suspend fun updateProfile(

        profileImage : Uri

    ) : Any
}