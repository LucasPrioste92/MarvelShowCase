package com.lucasprioste.marvelshowcase.presentation.detail_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.presentation.core.components.ComicItem
import com.lucasprioste.marvelshowcase.presentation.core.components.OnBottomReached
import com.lucasprioste.marvelshowcase.presentation.core.theme.PAGE_MARGIN_HORIZONTAL
import com.lucasprioste.marvelshowcase.presentation.core.theme.SPACE_AFTER_TITLE_DETAIL
import com.lucasprioste.marvelshowcase.presentation.detail_screen.components.HeaderCharacter
import com.lucasprioste.marvelshowcase.presentation.detail_screen.components.VerticalSection

@Composable
fun DetailScreen(
    navigator: NavController,
    viewModel: DetailViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val character = viewModel.character.collectAsState().value
    val comics = viewModel.comics.collectAsState().value
    val events = viewModel.events.collectAsState().value
    val stories = viewModel.stories.collectAsState().value
    val series = viewModel.series.collectAsState().value
    val paginationComics = viewModel.paginationComics.collectAsState().value
    val paginationEvents = viewModel.paginationEvents.collectAsState().value
    val paginationStories = viewModel.paginationStories.collectAsState().value
    val paginationSeries = viewModel.paginationSeries.collectAsState().value
    val action = viewModel.action.collectAsState().value

    LaunchedEffect(key1 = action){
        action?.let {
            viewModel.onEvent(DetailContract.DetailEvent.OnActionSeen)
            when(it){
                is DetailContract.DetailAction.ShowError -> {
                    Toast.makeText(context,context.getString(it.messageId), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val scrollStateComics = rememberLazyListState()

    scrollStateComics.OnBottomReached {
        viewModel.onEvent(DetailContract.DetailEvent.LoadMoreComics)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderCharacter(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            imageUri = character.thumbnail.getUriThumbnail(),
            name = character.name,
            onCloseClick = {
                navigator.popBackStack()
            },
            showShare = true,
            onShare = {
                val type = "text/plain"
                val extraText = character.urlShare
                val shareWith = context.getString(R.string.link_character)

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = type
                intent.putExtra(Intent.EXTRA_TEXT, extraText)

                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(intent, shareWith),
                    null
                )
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PAGE_MARGIN_HORIZONTAL)
                .padding(top = 18.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            item {
                if (character.description.isNotBlank()){
                    Text(
                        text = stringResource(id = R.string.overview),
                        style = MaterialTheme.typography.h1,
                    )
                    Spacer(modifier = Modifier.height(SPACE_AFTER_TITLE_DETAIL))
                    Text(
                        text = character.description,
                    )
                }
            }
            item {
                if (comics.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.comics),
                        style = MaterialTheme.typography.h1,
                    )
                    Spacer(modifier = Modifier.height(SPACE_AFTER_TITLE_DETAIL))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        state = scrollStateComics
                    ) {
                        items(
                            items = comics,
                            key = { item -> item.id }
                        ) {
                            ComicItem(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(265.dp),
                                comic = it
                            )
                        }
                        item {
                            AnimatedVisibility(visible = paginationComics.isLoading) {
                                Box(
                                    modifier = Modifier.height(260.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .align(Alignment.Center),
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            }
                        }
                    }
                }else if (paginationComics.isLoading){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        (1..4).map {
                            Box(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(250.dp)
                                    .shadow(elevation = 15.dp, shape = RoundedCornerShape(20.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }
            item {
                VerticalSection(
                    modifier = Modifier.fillMaxWidth(),
                    data = events,
                    titleSection = stringResource(id = R.string.events),
                    pagination = paginationEvents,
                    onLoadMore = {
                        viewModel.onEvent(DetailContract.DetailEvent.LoadMoreEvents)
                    }
                )
            }
            item {
                VerticalSection(
                    modifier = Modifier.fillMaxWidth(),
                    data = stories,
                    titleSection = stringResource(id = R.string.stories),
                    pagination = paginationStories,
                    onLoadMore = {
                        viewModel.onEvent(DetailContract.DetailEvent.LoadMoreStories)
                    }
                )
            }
            item {
                VerticalSection(
                    modifier = Modifier.fillMaxWidth(),
                    data = series,
                    titleSection = stringResource(id = R.string.series),
                    pagination = paginationSeries,
                    onLoadMore = {
                        viewModel.onEvent(DetailContract.DetailEvent.LoadMoreSeries)
                    }
                )

            }
        }
    }
}