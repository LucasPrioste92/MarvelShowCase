package com.lucasprioste.marvelshowcase.domain.model

import com.lucasprioste.marvelshowcase.core.AspectRatio

data class ThumbnailCharacter(
    val extension: String,
    val path: String
){
    fun getUriThumbnail(aspectRatio: AspectRatio = AspectRatio.Standard()): String{
        return "${path}/${aspectRatio.type}.${extension}"
    }
}