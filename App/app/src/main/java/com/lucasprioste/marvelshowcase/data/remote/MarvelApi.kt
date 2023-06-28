package com.lucasprioste.marvelshowcase.data.remote

import com.lucasprioste.marvelshowcase.core.md5
import com.lucasprioste.marvelshowcase.data.remote.dto.CharactersResponseDto
import retrofit2.http.GET
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

    companion object{
        const val PRIVATE_API_KEY = "ab83cb83c2ff8f6d37a3d2edf9182217cdb38d72"
        const val PUBLIC_API_KEY = "892df08f3fe8c333e2efa0b7de1fdd0a"
        const val BASE_URL = "http://gateway.marvel.com"
        val TS = Random.nextInt()
        val HASH = md5(TS.toString() + PRIVATE_API_KEY + PUBLIC_API_KEY)
    }
}