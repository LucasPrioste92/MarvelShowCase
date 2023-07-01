package com.lucasprioste.marvelshowcase.data

import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.repository.SessionRepository

class SessionRepositoryFake: SessionRepository {
    var characterStored: Character = Character()

    override fun getCharacter(): Character {
        return characterStored
    }

    override fun setCharacter(data: Character) {
        characterStored = data
    }
}