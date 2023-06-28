package com.lucasprioste.marvelshowcase.domain.model

import com.lucasprioste.marvelshowcase.core.GenericItemType

data class GenericItem(
    val name: String,
    val resourceURI: String,
    val type: GenericItemType? = null,
)