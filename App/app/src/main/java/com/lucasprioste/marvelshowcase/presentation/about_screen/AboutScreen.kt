package com.lucasprioste.marvelshowcase.presentation.about_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.presentation.about_screen.components.InfoAbout
import com.lucasprioste.marvelshowcase.presentation.core.components.ComicItem
import com.lucasprioste.marvelshowcase.presentation.core.components.OnBottomReached
import com.lucasprioste.marvelshowcase.presentation.core.theme.*
import com.lucasprioste.marvelshowcase.presentation.detail_screen.DetailContract

@Composable
fun AboutScreen(
    innerPadding: PaddingValues,
    viewModel: AboutViewModel = hiltViewModel(),
){
    val colorTitle = if (isSystemInDarkTheme()) White else BlackText

    val context = LocalContext.current

    val action = viewModel.action.collectAsState().value
    val pagination = viewModel.paginationComics.collectAsState().value
    val comics = viewModel.comics.collectAsState().value

    LaunchedEffect(key1 = action){
        action?.let {
            viewModel.onEvent(AboutContract.AboutEvent.OnActionSeen)
            when(it){
                is AboutContract.AboutAction.ShowError -> {
                    Toast.makeText(context,context.getString(it.messageId), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val scrollStateComics = rememberLazyListState()

    scrollStateComics.OnBottomReached {
        viewModel.onEvent(AboutContract.AboutEvent.LoadMore)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = PAGE_MARGIN_HORIZONTAL)
            .padding(top = PAGE_MARGIN_TOP),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.about_title),
                style = MaterialTheme.typography.h1.copy(color = colorTitle),
                fontSize = 36.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(36.dp))
            InfoAbout(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.we_can_be_heroes),
                body = stringResource(id = R.string.body_heroes),
                titleStyle = MaterialTheme.typography.h1.copy(color = colorTitle),
                bodyStyle = MaterialTheme.typography.body1.copy(fontStyle = FontStyle.Italic)
            )
            Spacer(modifier = Modifier.height(36.dp))
            InfoAbout(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.overview),
                body = stringResource(id = R.string.body_overview),
                titleStyle = MaterialTheme.typography.h1,
                bodyStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = stringResource(id = R.string.top_selling),
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = stringResource(id = R.string.link_selling),
            )
            Spacer(modifier = Modifier.height(18.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                state = scrollStateComics
            ) {
                items(
                    items = comics,
                    key = { item -> item.id }
                ) {
                    ComicItem(
                        modifier = Modifier
                            .width(150.dp)
                            .height(270.dp),
                        comic = it
                    )
                }
                item {
                    if (pagination.isLoading && comics.isEmpty()){
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            (1..4).map {
                                Box(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(270.dp)
                                        .shadow(elevation = 15.dp, shape = RoundedCornerShape(15.dp))
                                        .shimmerEffect()
                                )
                            }
                        }
                    }
                }
                item {
                    AnimatedVisibility(visible = pagination.isLoading) {
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
        }
    }
}