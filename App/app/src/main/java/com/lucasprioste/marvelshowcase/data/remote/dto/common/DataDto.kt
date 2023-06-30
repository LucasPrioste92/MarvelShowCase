package com.lucasprioste.marvelshowcase.data.remote.dto.common


data class DataDto(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<ItemDto>,
    val total: Int
)