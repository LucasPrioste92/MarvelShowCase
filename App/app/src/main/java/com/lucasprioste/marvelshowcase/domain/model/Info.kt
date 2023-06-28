package com.lucasprioste.marvelshowcase.domain.model

data class Info(
    val available: Int = 0,
    val collectionURI: String = "",
    val items: List<GenericItem> = emptyList(),
    val returned: Int = 0
)