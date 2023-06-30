package com.lucasprioste.marvelshowcase.presentation.about_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.marvelshowcase.core.paginator.DefaultPaginator
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.domain.model.pagination.PaginationInfo
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val repository: MarvelRepository,
): ViewModel() {

    private val _comics = MutableStateFlow(emptyList<ItemData>())
    val comics = _comics.asStateFlow()

    private val _paginationComics = MutableStateFlow(PaginationInfo())
    val paginationComics = _paginationComics.asStateFlow()

    private val _action = MutableStateFlow<AboutContract.AboutAction?>(null)
    val action = _action.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = paginationComics.value.page,
        onLoadUpdated = { load ->
            _paginationComics.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getComics(offset = nextPage, limit = 10)
        },
        getNextKey = {
            _paginationComics.update{ it.copy(page = it.page + 1) }
            _paginationComics.value.page
        },
        onError = { message ->
            _paginationComics.update { it.copy(error = message) }

        },
        onSuccess = { items, newKey ->
            _comics.update { oldItems -> oldItems + items }
            _paginationComics.update { it.copy(page = newKey, endReached = items.isEmpty()) }
        }
    )

    init {
        loadComicsNextItems()
    }

    fun onEvent(event: AboutContract.AboutEvent){
        when(event){
            AboutContract.AboutEvent.OnActionSeen -> _action.update { null }
            AboutContract.AboutEvent.LoadMore -> {
                loadComicsNextItems()
            }
        }
    }

    private fun loadComicsNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}