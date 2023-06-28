package com.lucasprioste.marvelshowcase.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.presentation.core.theme.PAGE_MARGIN_HORIZONTAL

@Composable
fun CardCharacter(
    modifier: Modifier = Modifier,
    name: String,
    imageUri: String,
    onCardClick: () -> Unit,
){
    val isLoadingImage = remember{
        mutableStateOf(true)
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = !isLoadingImage.value){
                onCardClick()
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ){
        Box {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUri,
                loading = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                    )
                },
                onLoading = {
                    isLoadingImage.value = true
                },
                onSuccess = {
                    isLoadingImage.value = false
                },
                onError = {isLoadingImage.value = false},
                contentDescription = stringResource(R.string.marvel_logo),
                contentScale = ContentScale.Crop
            )
            if (!isLoadingImage.value){
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f)
                        .background(color = MaterialTheme.colors.surface),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = PAGE_MARGIN_HORIZONTAL),
                        text = name,
                        style = MaterialTheme.typography.h2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}