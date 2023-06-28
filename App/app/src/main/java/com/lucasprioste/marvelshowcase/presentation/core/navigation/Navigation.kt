package com.lucasprioste.marvelshowcase.presentation.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

        }
        composable(route = Route.AboutScreen.route) {

        }
    }
}