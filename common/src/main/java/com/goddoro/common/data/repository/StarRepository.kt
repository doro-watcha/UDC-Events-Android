package com.goddoro.common.data.repository

import com.goddoro.common.data.model.Star

interface StarRepository {

    suspend fun createStar(
        classId : Int,
        point : Float
    ) : Any

    suspend fun deleteStar (
        starId : Int
    ) : Any
}