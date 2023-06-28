package com.lucasprioste.marvelshowcase.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.marvelshowcase.core.paginator.DefaultPaginator
import com.lucasprioste.marvelshowcase.domain.model.Character
import com.lucasprioste.marvelshowcase.domain.model.PaginationInfo
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MarvelRepository
): ViewModel() {

    private val _charactersList = MutableStateFlow((emptyList<Character>()))
    val charactersList = _charactersList.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()

    private val _pagination = MutableStateFlow(PaginationInfo())
    val pagination = _pagination.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = pagination.value.page,
        onLoadUpdated = { load ->
            _pagination.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getCharactersList(offset = nextPage, limit = 10)
        },
        getNextKey = {
            _pagination.update{ it.copy(page = it.page + 1) }
            _pagination.value.page
        },
        onError = { message ->
            _pagination.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            Log.d("AQUI", items.map { it.name }.toString())
            _charactersList.update { oldItems -> oldItems + items }
            _pagination.update { it.copy(page = newKey, endReached = items.isEmpty()) }
        }
    )

    init {
        loadNextItems()
    }

    fun onEvent(event: HomeContract.HomeEvent){
        when(event){
            is HomeContract.HomeEvent.OnSearchQueryChange -> {
                _searchInput.update { event.query }
            }
            HomeContract.HomeEvent.SearchCharacter -> {
                Log.d("AQUI","AQUI")
            }
            HomeContract.HomeEvent.LoadMore -> {
                loadNextItems()
            }
        }
    }

    private fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

}