package com.lucasprioste.marvelshowcase.presentation.detail_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.lucasprioste.marvelshowcase.core.shimmerEffect
import com.lucasprioste.marvelshowcase.domain.model.common.ItemData
import com.lucasprioste.marvelshowcase.domain.model.pagination.PaginationInfo
import com.lucasprioste.marvelshowcase.presentation.core.theme.SPACE_AFTER_TITLE_DETAIL
import com.lucasprioste.marvelshowcase.presentation.core.components.OnBottomReached

@Composable
fun VerticalSection(
    modifier: Modifier,
    data: List<ItemData>,
    titleSection: String,
    pagination: PaginationInfo,
    onLoadMore: () -> Unit,
){
    val scrollState = rememberLazyListState()

    scrollState.OnBottomReached {
        onLoadMore()
    }

    Column(
        modifier = modifier
    ) {
        if (data.isNotEmpty()){
            Text(
                text = titleSection,
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(SPACE_AFTER_TITLE_DETAIL))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 290.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                state = scrollState,
            ){
                itemsIndexed(
                    items = data,
                    key = { _, item -> item.id}
                ) { i, event ->
                    DefaultVerticalItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        showHr = i != data.size-1,
                        title = event.title,
                        description = event.description ?: "",
                        imageUri = event.thumbnail?.getUriThumbnail(),
                        widthImage = 51
                    )
                }
            }
            AnimatedVisibility(visible = pagination.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }else if (pagination.isLoading){
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                (1..4).map {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .shadow(elevation = 15.dp, shape = RoundedCornerShape(20.dp))
                            .shimmerEffect()
                    )
                }
            }
        }

    }
}