package com.lucasprioste.marvelshowcase.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.isImageNotAvailable
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.presentation.core.components.BottomName
import com.lucasprioste.marvelshowcase.presentation.core.components.ImageFromUri

@Composable
fun CardCharacter(
    modifier: Modifier = Modifier,
    name: String,
    imageUri: String,
    onCardClick: () -> Unit,
){
    val isLoadingImage = remember{
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = !isLoadingImage.value) {
                onCardClick()
            },
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp
    ){
        Box {
            ImageFromUri(
                modifier = Modifier.fillMaxSize(),
                imageUri = imageUri,
                onLoading = {
                    isLoadingImage.value = true
                },
                onSuccess = {
                    isLoadingImage.value = false
                },
                onError = {
                    isLoadingImage.value = false
                },
                description = name,
                contentScale = ContentScale.Crop
            )

            if (!isLoadingImage.value){
                BottomName(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f)
                        .background(color = MaterialTheme.colors.surface),
                    name = name,
                    showShareBtn = false,
                )
            }
        }
    }
}