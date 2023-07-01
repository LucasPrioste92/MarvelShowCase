package com.lucasprioste.marvelshowcase.data

import com.lucasprioste.marvelshowcase.core.OrderByCharacter
import com.lucasprioste.marvelshowcase.core.Resource
import com.lucasprioste.marvelshowcase.core.TypeDataRequest
import com.lucasprioste.marvelshowcase.data.mapper.character.toCharacter
import com.lucasprioste.marvelshowcase.data.mapper.common.toItem
import com.lucasprioste.marvelshowcase.data.remote.dto.characters.CharacterDto
import com.lucasprioste.marvelshowcase.data.remote.dto.common.ItemDto
import com.lucasprioste.marvelshowcase.data.remote.dto.common.ThumbnailDto
import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MarvelRepositoryFake: MarvelRepository {
    private val thumbnailDto = ThumbnailDto(extension = "", path = "")

    private val listCharacter = listOf(
        CharacterDto(description = "", name = "Iron Man", id = 0, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Hulk", id = 1, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Spider Man", id = 2, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "AAA", id = 3, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Iron", id = 4, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Teste", id = 5, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Thor", id = 6, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Jack", id = 7, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Ajak", id = 8, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Iron Gold", id = 9, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Teste12", id = 10, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Thor12", id = 11, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Jack12", id = 12, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Ajak12", id = 13, thumbnail = thumbnailDto, urls = emptyList()),
        CharacterDto(description = "", name = "Iron Gold12", id = 14, thumbnail = thumbnailDto, urls = emptyList()),
    )

    private val itemList = listOf(
        ItemDto(id = 0, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 1, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 2, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 3, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 4, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 5, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 6, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 7, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 8, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 9, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 10, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 11, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 12, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 13, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 14, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 15, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 16, description = "", thumbnail = thumbnailDto, title = ""),
        ItemDto(id = 17, description = "", thumbnail = thumbnailDto, title = ""),
    )

    override suspend fun getCharactersList(
        offset: Int,
        limit: Int,
        orderBy: OrderByCharacter?,
        nameStartsWith: String?
    ): Flow<Resource<List<Character>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = if (nameStartsWith != null) {
                    listCharacter.filter { it.name.uppercase().contains(nameStartsWith, ignoreCase = true) }
                } else {
                    listCharacter
                }
                val initialPosition = offset*limit
                val finalPosition = if ((offset*limit)+(limit-1) > response.size) response.size-1 else (offset*limit)+(limit-1)
                val pageItems = response.slice(initialPosition..finalPosition)
                emit(Resource.Success(data = pageItems.map { it.toCharacter() }))
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

    override suspend fun getDataRelatedToCharacter(
        offset: Int,
        limit: Int,
        characterID: Int,
        typeData: TypeDataRequest
    ): Flow<Resource<List<ItemData>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val initialPosition = offset*limit
                val finalPosition = if ((offset*limit)+(limit-1) > itemList.size) itemList.size-1 else (offset*limit)+(limit-1)
                val pageItems = itemList.slice(initialPosition..finalPosition)
                emit(Resource.Success(data = pageItems.map { it.toItem() }))
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

    override suspend fun getComics(offset: Int, limit: Int): Flow<Resource<List<ItemData>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val initialPosition = offset*limit
                val finalPosition = if ((offset*limit)+(limit-1) > itemList.size) itemList.size-1 else (offset*limit)+(limit-1)
                val pageItems = itemList.slice(initialPosition..finalPosition)
                emit(Resource.Success(data = pageItems.map { it.toItem() }))
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