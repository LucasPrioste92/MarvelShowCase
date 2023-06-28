package com.lucasprioste.marvelshowcase.domain.repository

import com.lucasprioste.marvelshowcase.core.OrderByCharacter
import com.lucasprioste.marvelshowcase.core.Resource
import com.lucasprioste.marvelshowcase.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun getCharactersList(
        offset: Int,
        limit: Int,
        orderBy: OrderByCharacter? = null,
        nameStartsWith: String? = null,
    ): Flow<Resource<List<Character>>>

}
