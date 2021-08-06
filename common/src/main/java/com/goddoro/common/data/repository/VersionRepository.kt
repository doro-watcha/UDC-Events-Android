package com.goddoro.common.data.repository

interface VersionRepository {

    suspend fun getMinimumVersion () : Int
}