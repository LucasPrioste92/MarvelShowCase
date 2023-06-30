package com.lucasprioste.marvelshowcase.domain.model.characters

import com.lucasprioste.marvelshowcase.domain.model.common.Thumbnail

data class Character(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
    val urlShare: String? = null,
)