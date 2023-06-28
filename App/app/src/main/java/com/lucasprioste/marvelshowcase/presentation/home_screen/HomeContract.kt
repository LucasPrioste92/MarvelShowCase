package com.lucasprioste.marvelshowcase.presentation.home_screen

sealed class HomeContract{
    sealed class HomeEvent{
        data class OnSearchQueryChange(val query: String): HomeEvent()
        object SearchCharacter: HomeEvent()
        object LoadMore: HomeEvent()
    }
    sealed class HomeAction{

    }
}