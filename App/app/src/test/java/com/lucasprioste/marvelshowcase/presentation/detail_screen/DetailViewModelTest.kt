package com.lucasprioste.marvelshowcase.presentation.detail_screen

import app.cash.turbine.test
import com.lucasprioste.marvelshowcase.MainDispatcherRule
import com.lucasprioste.marvelshowcase.data.MarvelRepositoryFake
import com.lucasprioste.marvelshowcase.data.SessionRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val sessionRepositoryFake = SessionRepositoryFake()
    private val marvelRepositoryFake = MarvelRepositoryFake()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        viewModel = DetailViewModel(
            repository = marvelRepositoryFake,
            sessionRepository = sessionRepositoryFake
        )
    }

    @Test
    fun `First Init`(): Unit = runTest {
        //Assert that values init correctly
        assert(viewModel.action.value == null)
        assert(viewModel.events.value.isNotEmpty())
        assert(viewModel.stories.value.isNotEmpty())
        assert(viewModel.series.value.isNotEmpty())
        assert(viewModel.comics.value.isNotEmpty())
    }

    @Test
    fun `Event Load More Events`(): Unit = runTest {
        // Load More
        val itemsBeforeLoadMore = viewModel.events.value
        viewModel.onEvent(DetailContract.DetailEvent.LoadMoreEvents)

        // Collect the response
        viewModel.events.test {
            val value = awaitItem()
            assert(value.isNotEmpty())
            assert(value.size > itemsBeforeLoadMore.size)
        }
    }
}