package com.lucasprioste.marvelshowcase.presentation.detail_screen

sealed class DetailContract{
    sealed class DetailEvent{
        object LoadMoreComics: DetailEvent()
        object LoadMoreEvents: DetailEvent()
        object LoadMoreStories: DetailEvent()
        object LoadMoreSeries: DetailEvent()
        object OnErrorSeen: DetailEvent()
    }
    sealed class DetailAction{
        data class ShowError(val messageId: Int): DetailAction()
    }
}