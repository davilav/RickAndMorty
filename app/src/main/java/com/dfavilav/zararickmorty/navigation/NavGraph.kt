package com.dfavilav.zararickmorty.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.dfavilav.zararickmorty.presentation.screens.home.HomeScreen
import com.dfavilav.zararickmorty.presentation.screens.splash.SplashScreen

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}