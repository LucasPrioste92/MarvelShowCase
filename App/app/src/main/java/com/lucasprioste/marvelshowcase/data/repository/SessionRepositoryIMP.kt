package com.lucasprioste.marvelshowcase.data.repository

import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.repository.SessionRepository

class SessionRepositoryIMP : SessionRepository {
    var characterStored: Character = Character()

    override fun getCharacter(): Character {
        return characterStored
    }

    override fun setCharacter(data: Character) {
        characterStored = data
    }
}