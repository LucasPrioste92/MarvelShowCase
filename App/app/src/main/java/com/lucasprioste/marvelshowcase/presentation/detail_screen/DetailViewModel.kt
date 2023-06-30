package com.lucasprioste.marvelshowcase.presentation.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasprioste.marvelshowcase.core.TypeDataRequest
import com.lucasprioste.marvelshowcase.core.paginator.DefaultPaginator
import com.lucasprioste.marvelshowcase.domain.model.characters.Character
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.domain.model.pagination.PaginationInfo
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import com.lucasprioste.marvelshowcase.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val sessionRepository: SessionRepository,
): ViewModel() {

    private val _character = MutableStateFlow(Character())
    val character = _character.asStateFlow()

    private val _comics = MutableStateFlow(emptyList<ItemData>())
    val comics = _comics.asStateFlow()

    private val _paginationComics = MutableStateFlow(PaginationInfo())
    val paginationComics = _paginationComics.asStateFlow()

    private val _events = MutableStateFlow(emptyList<ItemData>())
    val events = _events.asStateFlow()

    private val _paginationEvents = MutableStateFlow(PaginationInfo())
    val paginationEvents = _paginationEvents.asStateFlow()

    private val _stories = MutableStateFlow(emptyList<ItemData>())
    val stories = _stories.asStateFlow()

    private val _paginationStories = MutableStateFlow(PaginationInfo())
    val paginationStories = _paginationStories.asStateFlow()

    private val _series = MutableStateFlow(emptyList<ItemData>())
    val series = _series.asStateFlow()

    private val _paginationSeries = MutableStateFlow(PaginationInfo())
    val paginationSeries = _paginationSeries.asStateFlow()

    private val _action = MutableStateFlow<DetailContract.DetailAction?>(null)
    val action = _action.asStateFlow()

    private val paginatorComics = DefaultPaginator(
        initialKey = paginationComics.value.page,
        onLoadUpdated = { load ->
            _paginationComics.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getDataRelatedToCharacter(offset = nextPage, limit = 10, characterID = character.value.id, typeData = TypeDataRequest.Comics)
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

    private val paginatorEvents = DefaultPaginator(
        initialKey = paginationEvents.value.page,
        onLoadUpdated = { load ->
            _paginationEvents.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getDataRelatedToCharacter(offset = nextPage, limit = 10, characterID = character.value.id, typeData = TypeDataRequest.Events)
        },
        getNextKey = {
            _paginationEvents.update{ it.copy(page = it.page + 1) }
            _paginationEvents.value.page
        },
        onError = { message ->
            _paginationEvents.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _events.update { oldItems -> oldItems + items }
            _paginationEvents.update { it.copy(page = newKey, endReached = items.isEmpty()) }
        }
    )

    private val paginatorStories = DefaultPaginator(
        initialKey = paginationStories.value.page,
        onLoadUpdated = { load ->
            _paginationStories.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getDataRelatedToCharacter(offset = nextPage, limit = 10, characterID = character.value.id, typeData = TypeDataRequest.Stories)
        },
        getNextKey = {
            _paginationStories.update{ it.copy(page = it.page + 1) }
            _paginationStories.value.page
        },
        onError = { message ->
            _paginationStories.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _stories.update { oldItems -> oldItems + items }
            _paginationStories.update { it.copy(page = newKey, endReached = items.isEmpty()) }
        }
    )

    private val paginatorSeries = DefaultPaginator(
        initialKey = paginationSeries.value.page,
        onLoadUpdated = { load ->
            _paginationSeries.update { it.copy(isLoading = load) }
        },
        onRequest = { nextPage ->
            repository.getDataRelatedToCharacter(offset = nextPage, limit = 10, characterID = character.value.id, typeData = TypeDataRequest.Series)
        },
        getNextKey = {
            _paginationSeries.update{ it.copy(page = it.page + 1) }
            _paginationSeries.value.page
        },
        onError = { message ->
            _paginationSeries.update { it.copy(error = message) }
        },
        onSuccess = { items, newKey ->
            _series.update { oldItems -> oldItems + items }
            _paginationSeries.update { it.copy(page = newKey, endReached = items.isEmpty()) }
        }
    )

    init {
        _character.update { sessionRepository.getCharacter() }
        loadComicsNextItems()
        loadEventsNextItems()
        loadStoriesNextItems()
        loadSeriesNextItems()
    }

    fun onEvent(event: DetailContract.DetailEvent){
        when(event){
            DetailContract.DetailEvent.LoadMoreComics -> {
                if (!paginationComics.value.isLoading && comics.value.isNotEmpty() && !paginationComics.value.endReached)
                    loadComicsNextItems()
            }
            DetailContract.DetailEvent.LoadMoreEvents -> {
                if (!paginationEvents.value.isLoading && events.value.isNotEmpty() && !paginationEvents.value.endReached)
                    loadEventsNextItems()
            }
            DetailContract.DetailEvent.LoadMoreSeries -> {
                if (!paginationSeries.value.isLoading && series.value.isNotEmpty() && !paginationSeries.value.endReached)
                    loadSeriesNextItems()
            }
            DetailContract.DetailEvent.LoadMoreStories -> {
                if (!paginationStories.value.isLoading && stories.value.isNotEmpty() && !paginationStories.value.endReached)
                    loadStoriesNextItems()
            }
            DetailContract.DetailEvent.OnErrorSeen -> _action.update { null }
        }
    }

    private fun loadComicsNextItems(){
        viewModelScope.launch {
            paginatorComics.loadNextItems()
        }
    }

    private fun loadEventsNextItems(){
        viewModelScope.launch {
            paginatorEvents.loadNextItems()
        }
    }

    private fun loadStoriesNextItems(){
        viewModelScope.launch {
            paginatorStories.loadNextItems()
        }
    }

    private fun loadSeriesNextItems(){
        viewModelScope.launch {
            paginatorSeries.loadNextItems()
        }
    }

}