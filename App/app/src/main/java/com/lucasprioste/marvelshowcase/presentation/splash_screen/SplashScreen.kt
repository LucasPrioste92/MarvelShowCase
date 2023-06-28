package com.lucasprioste.marvelshowcase.presentation.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lucasprioste.marvelshowcase.R
import com.lucasprioste.marvelshowcase.presentation.core.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigator: NavHostController
){
    val context = LocalContext.current

    val scale by remember{
        mutableStateOf( Animatable(0f))
    }
    
    var textToShow by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(1200L)
        navigator.popBackStack()
        navigator.navigate(Route.HomeScreen.route)
    }
    
    LaunchedEffect(key1 = true){
        val rights = context.resources.getString(R.string.rigths)
        rights.map { char ->
            textToShow += char
            delay(50)
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.avengers_logo),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = stringResource(id = R.string.marvel_logo),
            modifier = Modifier.scale(scale.value)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            modifier = Modifier.padding(bottom = 60.dp),
            text = textToShow,
            style = MaterialTheme.typography.caption
        )
    }
}