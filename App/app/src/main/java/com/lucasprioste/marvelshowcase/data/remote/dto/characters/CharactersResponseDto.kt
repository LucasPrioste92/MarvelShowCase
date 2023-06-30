package com.lucasprioste.marvelshowcase.data.remote.dto.characters


data class CharactersResponseDto(
    val code: Int,
    val copyright: String,
    val data: CharactersListDto,
    val etag: String,
    val status: String
)