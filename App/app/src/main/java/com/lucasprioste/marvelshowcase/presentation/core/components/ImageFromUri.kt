package com.lucasprioste.marvelshowcase.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.isImageNotAvailable
import com.lucasprioste.marvelshowcase.core.shimmerEffect

@Composable
fun ImageFromUri(
    modifier: Modifier,
    imageUri: String,
    description: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    onLoading: (() -> Unit)? = null,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
){
    if (isImageNotAvailable(uri = imageUri)){
        Image(
            painter = painterResource(id = R.drawable.missing_event),
            contentDescription = description,
            modifier = modifier,
            contentScale = contentScale
        )
    }else{
        SubcomposeAsyncImage(
            modifier = modifier,
            model = imageUri,
            loading = {
                Box(modifier = modifier
                    .fillMaxSize()
                    .shimmerEffect()
                )
            },
            onLoading = {
                onLoading?.let {
                    onLoading()
                }
            },
            onSuccess = {
                onSuccess?.let {
                    onSuccess()
                }
            },
            onError = {
                onError?.let {
                    onError()
                }
            },
            contentDescription = description,
            contentScale = contentScale
        )
    }
}