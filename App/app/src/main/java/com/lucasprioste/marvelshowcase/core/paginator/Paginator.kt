package com.lucasprioste.marvelshowcase.core.paginator

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset(key: Key?)
}