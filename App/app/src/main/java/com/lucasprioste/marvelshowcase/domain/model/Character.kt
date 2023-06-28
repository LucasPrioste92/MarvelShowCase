package com.lucasprioste.marvelshowcase.domain.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailCharacter,
    val comics: Info,
    val events: Info,
    val series: Info,
    val stories: Info,
)