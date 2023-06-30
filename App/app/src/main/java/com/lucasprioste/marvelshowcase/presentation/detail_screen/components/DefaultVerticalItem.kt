package com.lucasprioste.marvelshowcase.presentation.detail_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.presentation.core.components.ImageFromUri

@Composable
fun DefaultVerticalItem(
    modifier: Modifier = Modifier,
    showHr: Boolean,
    title: String,
    description: String,
    imageUri: String? = null,
    widthImage: Int,
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = modifier
                .height(72.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageFromUri(
                modifier = Modifier
                    .width(widthImage.dp)
                    .fillMaxHeight()
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp)),
                imageUri = imageUri ?: "image_not_available",
                description = title + " " + stringResource(id = R.string.event_thumbnail)
            )
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    lineHeight = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (showHr)
            Divider(modifier = Modifier.height(2.dp).fillMaxWidth(), color = MaterialTheme.colors.primary)
    }
}