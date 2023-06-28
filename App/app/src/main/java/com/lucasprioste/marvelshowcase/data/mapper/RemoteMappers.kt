package com.lucasprioste.marvelshowcase.data.mapper

import com.lucasprioste.marvelshowcase.core.GenericItemType
import com.lucasprioste.marvelshowcase.data.remote.dto.*
import com.lucasprioste.marvelshowcase.domain.model.*

fun CharactersListDto.toCharactersList(): CharactersList{
    return CharactersList(
        charactersList = results.map { it.toCharacter() },
        countCharacters = count,
        charactersPerPage = limit,
        totalCharacters = total,
        offset = offset,
    )
}

private fun CharacterDto.toCharacter(): Character{
    return Character(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.toThumbnailCharacter(),
        comics = comics.toInfo(),
        events = events.toInfo(),
        series = series.toInfo(),
        stories = stories.toInfo(),
    )
}

private fun ThumbnailDto.toThumbnailCharacter(): ThumbnailCharacter{
    return ThumbnailCharacter(
        path = path,
        extension = extension
    )
}

private fun InfoDto.toInfo(): Info{
    return Info(
        available = available,
        collectionURI = collectionURI,
        items = items.map { it.toGenericItem() },
        returned = returned,
    )
}

private fun GenericItemDto.toGenericItem(): GenericItem{
    return GenericItem(
        name = name,
        resourceURI = resourceURI,
        type = getTypeGenericItem(type)
    )
}

private fun getTypeGenericItem(type: String?): GenericItemType?{
    return when(type){
        "cover" -> GenericItemType.COVER
        "interior" -> GenericItemType.INTERIOR
        else -> null
    }
}

