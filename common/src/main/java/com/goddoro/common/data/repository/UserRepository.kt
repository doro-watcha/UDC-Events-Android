package com.goddoro.common.data.repository

import com.goddoro.common.data.model.User


/**
 * created By DORO 2020/10/10
 */

interface UserRepository {

    suspend fun getUser (
        userId : Int
    ) : User
}