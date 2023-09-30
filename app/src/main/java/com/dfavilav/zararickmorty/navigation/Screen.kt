package com.dfavilav.zararickmorty.navigation

sealed class Screen(val route: String) {

    data object Splash : Screen("splash_screen")
}