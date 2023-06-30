package com.lucasprioste.marvelshowcase.presentation.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.presentation.core.theme.PAGE_MARGIN_HORIZONTAL

@Composable
fun BottomName(
    modifier: Modifier = Modifier,
    fontSize: Int? = null,
    name: String,
    showShareBtn: Boolean,
    onShareClick: (() -> Unit)? = null,
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PAGE_MARGIN_HORIZONTAL),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSize?.sp ?: 20.sp,
                color = MaterialTheme.colors.onSurface
            )
            if (showShareBtn){
                Icon(
                    painter = painterResource(id = R.drawable.share_icon),
                    contentDescription = stringResource(id = R.string.share_icon),
                    modifier = Modifier
                        .clickable {
                            onShareClick?.let { it() }
                        },
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}