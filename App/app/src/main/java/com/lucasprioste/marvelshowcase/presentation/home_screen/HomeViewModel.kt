package com.lucasprioste.marvelshowcase.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.paginator.DefaultPaginator
import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.model.pagination.PaginationInfo
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import com.lucasprioste.marvelshowcase.domain.repository.SessionRepository
import com.lucasprioste.marvelshowcase.presentation.home_screen.HomeContract.HomeAction.NavigateToCharacterScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val sessionRepository: SessionRepository,
): ViewModel() {

    private val _charactersList = MutableStateFlow((emptyList<Character>()))
    val charactersList = _charactersList.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()

    private val _pagination = MutableStateFlow(PaginationInfo())
    val pagination = _pagination.asStateFlow()

    private val _action = MutableStateFlow<HomeContract.HomeAction?>(null)
    val action = _action.asStateFlow()

    private var listBeforeSearch = emptyList<Character>()
    private var paginationBeforeSearch: PaginationInfo? = null

    private val paginator = DefaultPaginator(
        initialKey = _pagination.value.page,
        onLoadUpdated = { load ->
            _pagination.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            val search = searchInput.value.ifBlank { null }
            repository.getCharactersList(offset = nextPage, limit = 10, nameStartsWith = search)
        },
        getNextKey = {
            _pagination.update{ it.copy(page = it.page + 1) }
            _pagination.value.page
        },
        onError = { message ->
            _pagination.update { it.copy(error = message) }
            _action.update { HomeContract.HomeAction.Error(msg = R.string.something_went_wrong) }
        },
        onSuccess = { items, newKey ->
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
                if (searchInput.value.isNotBlank()){
                    listBeforeSearch = charactersList.value
                    paginationBeforeSearch = pagination.value
                    _pagination.update { PaginationInfo() }
                    _charactersList.update { emptyList() }
                    paginator.reset(key = 0)
                    loadNextItems()
                }else if (listBeforeSearch.isNotEmpty()){
                    _pagination.update { paginationBeforeSearch ?: PaginationInfo() }
                    _charactersList.update { listBeforeSearch }
                    paginator.reset(key = paginationBeforeSearch?.page ?: 0)
                    listBeforeSearch = emptyList()
                }
            }
            HomeContract.HomeEvent.LoadMore -> {
                if (!pagination.value.isLoading && charactersList.value.isNotEmpty() && !pagination.value.endReached) {
                    loadNextItems()
                }
            }
            is HomeContract.HomeEvent.OnCharacterClick -> {
                sessionRepository.setCharacter(data = event.character)
                _action.update { NavigateToCharacterScreen }
            }
            HomeContract.HomeEvent.OnActionSeen -> { _action.update { null } }
        }
    }

    private fun loadNextItems(){
        viewModelScope.launch(Dispatchers.IO) {
            paginator.loadNextItems()
        }
    }

}