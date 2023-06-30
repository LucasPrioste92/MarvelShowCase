package com.lucasprioste.marvelshowcase.data.remote.dto.characters

data class CharactersListDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharacterDto>,
    val total: Int
)