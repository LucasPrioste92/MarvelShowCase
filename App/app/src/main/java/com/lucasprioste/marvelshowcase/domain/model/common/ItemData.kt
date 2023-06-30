package com.lucasprioste.marvelshowcase.domain.model.common

data class ItemData(
    val id: Int,
    val title: String,
    val description: String? = null,
    val thumbnail: Thumbnail? = null,
)
