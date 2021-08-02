package com.goddoro.common.data.repositoryImpl

import com.goddoro.common.data.api.GenreAPI
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.repository.GenreRepository

class GenreRepositoryImpl ( private val api : GenreAPI) : GenreRepository {
    override suspend fun listGenre(): List<Genre> {
        return api.listGenres().data?.genres ?: listOf()
    }
}