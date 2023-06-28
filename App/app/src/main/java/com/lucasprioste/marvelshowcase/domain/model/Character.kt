package com.lucasprioste.marvelshowcase.domain.model

data class Character(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val thumbnail: ThumbnailCharacter = ThumbnailCharacter(),
    val comics: Info = Info(),
    val events: Info = Info(),
    val series: Info = Info(),
    val stories: Info = Info(),
)