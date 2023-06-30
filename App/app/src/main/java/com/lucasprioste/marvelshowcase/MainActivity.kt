package com.lucasprioste.marvelshowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
    Scaffold(
        bottomBar = {
            BottomNavigation(
                navController = navController
            )
        }
    ) { innerPadding ->
        Navigation(
            innerPadding = innerPadding,
            navController = navController
        )
    }
}