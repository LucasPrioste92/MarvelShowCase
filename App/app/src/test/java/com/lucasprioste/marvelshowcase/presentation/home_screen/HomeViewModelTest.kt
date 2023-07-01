package com.lucasprioste.marvelshowcase.presentation.home_screen

import app.cash.turbine.test
import com.lucasprioste.marvelshowcase.MainDispatcherRule
import com.lucasprioste.marvelshowcase.data.MarvelRepositoryFake
import com.lucasprioste.marvelshowcase.data.SessionRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val sessionRepositoryFake = SessionRepositoryFake()
    private val marvelRepositoryFake = MarvelRepositoryFake()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        viewModel = HomeViewModel(
            repository = marvelRepositoryFake,
            sessionRepository = sessionRepositoryFake
        )
    }

    @Test
    fun `First Init`(): Unit = runTest {
        //Assert that values init correctly
        assert(viewModel.action.value == null)
        assert(viewModel.charactersList.value.isNotEmpty())
    }

    @Test
    fun `Search Event`(): Unit = runTest {
        // Search for iron string
        viewModel.onEvent(HomeContract.HomeEvent.OnSearchQueryChange("iron"))
        viewModel.onEvent(HomeContract.HomeEvent.SearchCharacter)

        // Collect the response
        viewModel.charactersList.test {
            val value = awaitItem()
            assert(value.isNotEmpty())
            value.map { assert(it.name.uppercase().contains("iron", ignoreCase = true)) }
        }
    }

    @Test
    fun `Event On Character Click`(): Unit = runTest {
        // Search for iron string
        val character = viewModel.charactersList.value.first()
        viewModel.onEvent(HomeContract.HomeEvent.OnCharacterClick(character))

        // Receive stored Character
        val stored = sessionRepositoryFake.getCharacter()
        assert(character == stored)
    }

    @Test
    fun `Event Load More Characters`(): Unit = runTest {
        // Load More
        val itemsBeforeLoadMore = viewModel.charactersList.value
        viewModel.onEvent(HomeContract.HomeEvent.LoadMore)

        // Collect the response
        viewModel.charactersList.test {
            val value = awaitItem()
            assert(value.isNotEmpty())
            assert(value.size > itemsBeforeLoadMore.size)
        }
    }
}