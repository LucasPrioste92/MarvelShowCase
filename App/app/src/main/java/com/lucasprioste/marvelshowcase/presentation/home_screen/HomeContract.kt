package com.lucasprioste.marvelshowcase.presentation.home_screen

import com.lucasprioste.marvelshowcase.domain.model.characters.Character

sealed class HomeContract{
    sealed class HomeEvent{
        data class OnSearchQueryChange(val query: String): HomeEvent()
        object SearchCharacter: HomeEvent()
        object LoadMore: HomeEvent()
        data class OnCharacterClick(val character: Character): HomeEvent()
        object OnActionSeen: HomeEvent()
    }
    sealed class HomeAction{
        object NavigateToCharacterScreen: HomeAction()
        data class Error(val msg: Int): HomeAction()
    }
}