package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.data.api.UserAPI
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.UserRepository


/**
 * created By DORO 2020/10/10
 */

class UserRepositoryImpl ( val api : UserAPI): UserRepository {

    override suspend fun getUser(userId: Int): User {

        return api.getUser(userId).unWrapData().user
    }


}

