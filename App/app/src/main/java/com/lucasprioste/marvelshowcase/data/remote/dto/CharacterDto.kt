package com.lucasprioste.marvelshowcase.data.remote.dto

data class CharacterDto(
    val comics: InfoDto,
    val description: String,
    val events: InfoDto,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: InfoDto,
    val stories: InfoDto,
    val thumbnail: ThumbnailDto,
    val urls: List<UrlDto>
)