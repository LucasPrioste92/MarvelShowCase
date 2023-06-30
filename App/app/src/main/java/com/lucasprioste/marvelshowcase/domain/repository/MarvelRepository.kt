package com.lucasprioste.marvelshowcase.domain.repository

import com.lucasprioste.marvelshowcase.core.OrderByCharacter
import com.lucasprioste.marvelshowcase.core.Resource
import com.lucasprioste.marvelshowcase.core.TypeDataRequest
import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    suspend fun getCharactersList(
        offset: Int,
        limit: Int,
        orderBy: OrderByCharacter? = null,
        nameStartsWith: String? = null,
    ): Flow<Resource<List<Character>>>

    suspend fun getDataRelatedToCharacter(
        offset: Int,
        limit: Int,
        characterID: Int,
        typeData: TypeDataRequest,
    ): Flow<Resource<List<ItemData>>>

    suspend fun getComics(
        offset: Int,
        limit: Int,
    ): Flow<Resource<List<ItemData>>>

}
