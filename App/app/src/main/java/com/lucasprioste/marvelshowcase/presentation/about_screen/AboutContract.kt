package com.lucasprioste.marvelshowcase.presentation.about_screen

sealed class AboutContract{
    sealed class AboutEvent{
        object LoadMore: AboutEvent()
        object OnActionSeen: AboutEvent()
    }
    sealed class AboutAction{
        data class ShowError(val messageId: Int): AboutAction()
    }
}