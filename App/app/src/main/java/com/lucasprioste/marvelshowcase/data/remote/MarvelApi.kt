package com.lucasprioste.marvelshowcase.data.remote

import com.lucasprioste.marvelshowcase.BuildConfig
import com.lucasprioste.marvelshowcase.core.md5
import com.lucasprioste.marvelshowcase.data.remote.dto.characters.CharactersResponseDto
import com.lucasprioste.marvelshowcase.data.remote.dto.common.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.random.Random

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("nameStartsWith") nameStartsWith: String?,
    ): CharactersResponseDto

    @GET("/v1/public/characters/{id}/{type}")
    suspend fun getDataRelatedToCharacter(
        @Path("id") characterId: Int,
        @Path("type") type: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): ResponseDto

    companion object{
        const val BASE_URL = "http://gateway.marvel.com"
        val TS = Random.nextInt()
        val HASH = md5(TS.toString() + BuildConfig.PRIVATE_API_KEY + BuildConfig.PUBLIC_API_KEY)
    }
}