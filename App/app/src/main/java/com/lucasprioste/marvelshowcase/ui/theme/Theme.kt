package com.lucasprioste.marvelshowcase.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = GrayMedium,
    onPrimary = White,
    background = BlackBG,
    onBackground = White,
    surface = Purple,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = GrayLight,
    onPrimary = BlackText,
    background = White,
    onBackground = GrayLight,
    surface = Purple,
    onSurface = White,
)

@Composable
fun MarvelShowCaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}