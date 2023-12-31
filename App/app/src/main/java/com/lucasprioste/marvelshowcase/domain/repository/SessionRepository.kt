package com.lucasprioste.marvelshowcase.domain.repository

import com.lucasprioste.marvelshowcase.domain.model.characters.Character

interface SessionRepository {
    fun getCharacter(): Character
    fun setCharacter(data: Character)
}