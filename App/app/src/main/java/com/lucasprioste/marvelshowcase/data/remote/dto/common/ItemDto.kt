package com.lucasprioste.marvelshowcase.data.remote.dto.common

data class ItemDto(
    val id: Int,
    val description: String? = null,
    val thumbnail: ThumbnailDto? = null,
    val title: String,
)