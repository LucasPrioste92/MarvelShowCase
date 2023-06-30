package com.lucasprioste.marvelshowcase.data.remote.dto.characters

import androidx.annotation.Keep

@Keep
data class UrlDto(
    val type: String,
    val url: String
)