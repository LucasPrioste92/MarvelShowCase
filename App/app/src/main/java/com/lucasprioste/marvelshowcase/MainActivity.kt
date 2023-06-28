package com.lucasprioste.marvelshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lucasprioste.marvelshowcase.presentation.core.navigation.BottomNavigation
import com.lucasprioste.marvelshowcase.presentation.core.navigation.Navigation
import com.lucasprioste.marvelshowcase.presentation.core.navigation.Route
import com.lucasprioste.marvelshowcase.presentation.core.theme.MarvelShowCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelShowCaseTheme {

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colors.primary
                )

                MarvelShowCaseApp()
            }
        }
    }
}

@Composable
fun MarvelShowCaseApp(
    navController: NavHostController = rememberNavController()
){
    val bottomBarState = rememberSaveable {
        mutableStateOf(true)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Route.HomeScreen.route, Route.AboutScreen.route -> {
            bottomBarState.value = true
        }
        else -> bottomBarState.value = false
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                bottomBarState = bottomBarState,
                navController = navController
            )
        }
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding = innerPadding)
    }
}