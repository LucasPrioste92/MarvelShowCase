package com.lucasprioste.marvelshowcase.data.repository

import com.lucasprioste.marvelshowcase.core.OrderByCharacter
import com.lucasprioste.marvelshowcase.core.Resource
import com.lucasprioste.marvelshowcase.data.mapper.toCharactersList
import com.lucasprioste.marvelshowcase.data.remote.MarvelApi
import com.lucasprioste.marvelshowcase.domain.model.Character
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepositoryIMP @Inject constructor(
    private val api: MarvelApi
): MarvelRepository {

    override suspend fun getCharactersList(
        offset: Int,
        limit: Int,
        orderBy: OrderByCharacter?,
        nameStartsWith: String?
    ): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getCharacters(
                    offset = offset * limit,
                    limit = limit,
                    orderBy = orderBy?.type ?: OrderByCharacter.NameAscending.type,
                    nameStartsWith = nameStartsWith
                )
                emit(Resource.Success(response.data.toCharactersList().charactersList))
            }catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Something Went Wrong"))
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = e.message ?: "Request Error"))
            }
            emit(Resource.Loading(false))
        }
    }

}