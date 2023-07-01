package com.lucasprioste.marvelshowcase.data.mapper.character


import com.lucasprioste.marvelshowcase.data.mapper.common.toThumbnail
import com.lucasprioste.marvelshowcase.data.remote.dto.characters.CharacterDto
import com.lucasprioste.marvelshowcase.data.remote.dto.characters.CharactersListDto
import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.model.characters.CharactersList


fun CharactersListDto.toCharactersList(): CharactersList {
    return CharactersList(
        charactersList = results.map { it.toCharacter() },
        countCharacters = count,
        charactersPerPage = limit,
        totalCharacters = total,
        offset = offset,
    )
}

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.toThumbnail(),
        urlShare = urls.find { it.type.contains("comiclink") }?.url
    )
}
