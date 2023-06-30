package com.lucasprioste.marvelshowcase.presentation.core.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lucasprioste.marvelshowcase.R

val arialFontFamily = FontFamily(
    Font(resId = R.font.arial_mt, weight = FontWeight.Normal),
    Font(resId = R.font.arial_bold_mt, weight = FontWeight.Bold),
)
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = arialFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp
    ),
    h2 = TextStyle(
        fontFamily = arialFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = arialFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = arialFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = arialFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)