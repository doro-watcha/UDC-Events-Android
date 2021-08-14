package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.StarAPI
import com.goddoro.common.data.model.Star
import com.goddoro.common.data.repository.StarRepository

class StarRepositoryImpl ( private val api : StarAPI) : StarRepository {
    override suspend fun createStar(classId: Int, point: Float): Any {
        val params = hashMapOf(
            "classId" to classId,
            "point" to point
        ).filterValueNotNull()

        return api.createStar(params).unWrapCompletable()
    }

    override suspend fun deleteStar(starId: Int): Any {
        return api.deleteStar(starId).unWrapCompletable()
    }
}