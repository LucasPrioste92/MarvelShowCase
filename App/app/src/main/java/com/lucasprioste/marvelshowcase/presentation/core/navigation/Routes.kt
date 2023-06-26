package com.lucasprioste.marvelshowcase.presentation.core.navigation

import com.lucasprioste.marvelshowcase.R

sealed class Route(val route: String){
    object SplashScreen: Route("splash_screen")
    object HomeScreen: Route("home_screen")
    object DetailScreen: Route("detail_screen")
    object AboutScreen: Route("about_screen")
}

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
){
    object Home: BottomNavItem(Route.HomeScreen.route, R.drawable.tab_homepage)
    object About: BottomNavItem(Route.AboutScreen.route, R.drawable.tab_about)
}