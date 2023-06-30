package com.lucasprioste.marvelshowcase.presentation.detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.core.AspectRatio
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.presentation.core.components.ImageFromUri

@Composable
fun ComicItem(
    modifier: Modifier = Modifier,
    comic: ItemData,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ImageFromUri(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp)),
            imageUri = comic.thumbnail!!.getUriThumbnail(aspectRatio = AspectRatio.Portrait),
            description = comic.title + " " + stringResource(id = R.string.comic_thumbnail)
        )
        Text(
            text = comic.title,
            lineHeight = 24.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}