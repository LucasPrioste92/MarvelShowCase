package com.lucasprioste.marvelshowcase.data.remote.dto.characters

import com.lucasprioste.marvelshowcase.data.remote.dto.common.ThumbnailDto

data class CharacterDto(
    val description: String,
    val id: Int,
    val name: String,
    val thumbnail: ThumbnailDto,
    val urls: List<UrlDto>
)