package com.lucasprioste.marvelshowcase.domain.model.pagination

data class PaginationInfo(
    val isLoading: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
