package com.lucasprioste.marvelshowcase.domain.model.common

import com.lucasprioste.marvelshowcase.core.AspectRatio

data class Thumbnail(
    val extension: String = "",
    val path: String = ""
){
    fun getUriThumbnail(aspectRatio: AspectRatio = AspectRatio.Standard): String{
        val hasPointExtension = extension.contains(".")
        val newExtension = if (hasPointExtension) extension else ".$extension"
        return "${path}/${aspectRatio.type}${newExtension}"
    }
}