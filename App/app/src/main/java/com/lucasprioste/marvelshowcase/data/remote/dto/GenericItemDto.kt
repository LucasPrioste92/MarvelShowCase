package com.lucasprioste.marvelshowcase.data.remote.dto

data class GenericItemDto(
    val name: String,
    val resourceURI: String,
    val type: String? = null
)