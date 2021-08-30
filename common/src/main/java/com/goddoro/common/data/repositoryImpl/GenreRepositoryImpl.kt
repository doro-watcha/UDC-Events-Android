package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.GenreAPI
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.GenreRepository

class GenreRepositoryImpl ( private val api : GenreAPI) : GenreRepository {
    override suspend fun listGenre( sort : String? ): List<Genre> {

        val params = hashMapOf(
            "sort" to sort
        ).filterValueNotNull()
        return api.listGenres(params).data?.genres ?: listOf()
    }
}