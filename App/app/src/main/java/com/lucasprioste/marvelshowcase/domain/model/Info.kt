package com.lucasprioste.marvelshowcase.domain.model

data class Info(
    val available: Int,
    val collectionURI: String,
    val items: List<GenericItem>,
    val returned: Int
)