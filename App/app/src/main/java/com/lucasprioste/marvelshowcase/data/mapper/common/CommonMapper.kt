package com.lucasprioste.marvelshowcase.data.mapper.common

import com.lucasprioste.marvelshowcase.data.remote.dto.common.ItemDto
import com.lucasprioste.marvelshowcase.data.remote.dto.common.ResponseDefaultDto
import com.lucasprioste.marvelshowcase.data.remote.dto.common.ThumbnailDto
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.domain.model.common.Thumbnail


fun ThumbnailDto.toThumbnail(): Thumbnail {
    return Thumbnail(
        path = path,
        extension = extension
    )
}

fun ResponseDefaultDto.toItemList(): List<ItemData> {
    return data.results.map { it.toEvent() }
}

fun ItemDto.toEvent(): ItemData {
    return ItemData(
        id = id,
        title = title,
        description = description,
        thumbnail = thumbnail?.toThumbnail()
    )
}
