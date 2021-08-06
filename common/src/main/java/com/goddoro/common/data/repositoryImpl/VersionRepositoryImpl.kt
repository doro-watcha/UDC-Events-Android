package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.data.api.VersionAPI
import com.goddoro.common.data.repository.VersionRepository

class VersionRepositoryImpl ( private val api : VersionAPI) : VersionRepository {

    override suspend fun getMinimumVersion(): Int {
        return api.getMinimumVersion().data?.version ?: -1
    }
}