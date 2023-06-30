package com.lucasprioste.marvelshowcase.presentation.detail_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.presentation.core.components.BottomName
import com.lucasprioste.marvelshowcase.presentation.core.components.ImageFromUri
import com.lucasprioste.marvelshowcase.presentation.core.theme.PAGE_MARGIN_HORIZONTAL

@Composable
fun HeaderCharacter(
    modifier: Modifier = Modifier,
    name: String,
    imageUri: String,
    onCloseClick: () -> Unit,
    showShare: Boolean,
    onShare: (() -> Unit)? = null,
){
    Box(modifier = modifier){
        ImageFromUri(
            modifier = Modifier.fillMaxSize(),
            imageUri = imageUri,
            description = name,
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.close_icon),
            contentDescription = stringResource(id = R.string.close_icon),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = PAGE_MARGIN_HORIZONTAL, top = 15.dp)
                .shadow(elevation = 15.dp, shape = RoundedCornerShape(15.dp))
                .clickable {
                    onCloseClick()
                },
        )
        BottomName(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .clip(shape = RoundedCornerShape(25.dp,25.dp,0.dp,0.dp))
                .background(color = MaterialTheme.colors.surface),
            fontSize = 27,
            name = name,
            showShareBtn = showShare,
            onShareClick = onShare
        )
    }
}