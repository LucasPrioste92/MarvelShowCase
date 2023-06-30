package com.lucasprioste.marvelshowcase.presentation.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lucasprioste.marvelshowcase.presentation.about_screen.AboutScreen
import com.lucasprioste.marvelshowcase.presentation.core.theme.*
import com.lucasprioste.marvelshowcase.presentation.detail_screen.DetailScreen
import com.lucasprioste.marvelshowcase.presentation.home_screen.HomeScreen
import com.lucasprioste.marvelshowcase.presentation.splash_screen.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route,
    ) {
        composable(route = Route.SplashScreen.route) {
            SplashScreen(navigator = navController)
        }
        composable(route = Route.HomeScreen.route) {
            HomeScreen(navigator = navController, innerPadding = innerPadding)
        }
        composable(route = Route.DetailScreen.route) {
            DetailScreen(navigator = navController)
        }
        composable(route = Route.AboutScreen.route) {
            AboutScreen(innerPadding = innerPadding)
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val items by remember {
        mutableStateOf(
            listOf(
                BottomNavItem.Home,
                BottomNavItem.About,
            )
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val menuColor = if(!isSystemInDarkTheme()) GrayLight else GrayMedium

    var bottomBarState by remember {
        mutableStateOf(false)
    }

    bottomBarState = when (currentRoute) {
        Route.HomeScreen.route, Route.AboutScreen.route -> {
            true
        }
        else -> false
    }

    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Red, Pink
                                )
                            )
                        )
                )
                BottomAppBar(
                    backgroundColor = menuColor
                ) {
                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    val color = if (isSystemInDarkTheme()) White else Purple
                                    if (currentRoute == item.route){
                                        Box(contentAlignment = Alignment.Center) {
                                            Box(modifier = Modifier
                                                .width(40.dp)
                                                .height(40.dp)
                                                .background(
                                                    alpha = 0.2f,
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            color,
                                                            color,
                                                            menuColor
                                                        )
                                                    ),
                                                )
                                            )
                                            Icon(
                                                painterResource(id = item.icon),
                                                tint = color,
                                                contentDescription = null,
                                            )
                                        }

                                    }else{
                                        Icon(
                                            painterResource(id = item.icon),
                                            tint = color,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            },
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Route.HomeScreen.route) {
                                        saveState = false
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selected = currentRoute == item.route
                        )
                    }
                }
            }
        }
    )
}