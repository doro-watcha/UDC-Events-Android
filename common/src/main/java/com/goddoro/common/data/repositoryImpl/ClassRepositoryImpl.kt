package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.ClassAPI
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.ClassRepository


/**
 * created By DORO 12/27/20
 */

class ClassRepositoryImpl ( val api : ClassAPI) : ClassRepository {
    override suspend fun listClasses( sort : String?, type : String?): List<DanceClass> {

        val params = hashMapOf(
            "sort" to sort,
            "classType" to type
        ).filterValueNotNull()

        return api.listClasses(params).unWrapData().classes
    }

    override suspend fun getClass(id: Int): DanceClass {

        return api.getClass(id).unWrapData().danceClass

    }


    override suspend fun registerClass(): Any {
        TODO("Not yet implemented")
    }

}