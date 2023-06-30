package com.lucasprioste.marvelshowcase.presentation.about_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun InfoAbout(
    modifier: Modifier,
    title: String,
    body: String,
    titleStyle: TextStyle,
    bodyStyle: TextStyle
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(17.dp)
    ) {
        Text(
            text = title,
            style = titleStyle,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = body,
            style = bodyStyle,
            modifier = Modifier.fillMaxWidth()
        )
    }
}