package com.lucasprioste.marvelshowcase.domain.model

data class CharactersList(
    val countCharacters: Int = 0,
    val charactersPerPage: Int = 0,
    val offset: Int = 0,
    val totalCharacters: Int = 0,
    val charactersList: List<Character> = emptyList(),
    val isLoading: Boolean = true,
)