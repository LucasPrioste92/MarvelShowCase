package com.lucasprioste.marvelshowcase.presentation.home_screen


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.AspectRatio
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.presentation.core.theme.*
import com.lucasprioste.marvelshowcase.presentation.home_screen.HomeContract.*
import com.lucasprioste.marvelshowcase.presentation.home_screen.components.CardCharacter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navigator: NavHostController,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val searchInput = viewModel.searchInput.collectAsState().value
    val charactersList = viewModel.charactersList.collectAsState().value
    val paginationInfo = viewModel.pagination.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = PAGE_MARGIN_HORIZONTAL)
            .padding(top = PAGE_MARGIN_TOP, bottom = PAGE_MARGIN_BOTTOM),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.home_title),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Image(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(35.dp)
                    .clip(shape = Shapes.small),
                painter = painterResource(id = R.drawable.marvel_logo),
                contentDescription = stringResource(id = R.string.marvel_logo),
            )
        }
        OutlinedTextField(
            value = searchInput,
            onValueChange = {
                viewModel.onEvent(
                    HomeEvent.OnSearchQueryChange(it)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = stringResource(id = R.string.search_icon),
                    tint = MaterialTheme.colors.onPrimary
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.onEvent(HomeEvent.SearchCharacter)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
            ),
            shape = Shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary, shape = Shapes.large),
            textStyle = Typography.h3,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    color = MaterialTheme.colors.onPrimary,
                    style = Typography.h3,
                )
            },
            maxLines = 1,
            singleLine = true
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(31.dp)
        ){
            items(charactersList){ item ->
                CardCharacter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp),
                    name = item.name,
                    imageUri = item.thumbnail.getUriThumbnail(aspectRatio = AspectRatio.Landscape()),
                    onCardClick = {}
                )
            }
            item {
                if (charactersList.isEmpty() && paginationInfo.isLoading){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(31.dp)
                    ) {
                        (1..4).map {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(185.dp)
                                    .shadow(elevation = 25.dp, shape = RoundedCornerShape(20.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }else if(paginationInfo.isLoading){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }
}