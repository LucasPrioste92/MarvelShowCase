package com.lucasprioste.marvelshowcase.data.remote.dto

data class InfoDto(
    val available: Int,
    val collectionURI: String,
    val items: List<GenericItemDto>,
    val returned: Int
)