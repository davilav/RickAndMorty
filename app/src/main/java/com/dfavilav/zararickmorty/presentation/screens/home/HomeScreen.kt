package com.dfavilav.zararickmorty.presentation.screens.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.dfavilav.zararickmorty.presentation.common.ListContent
import com.dfavilav.zararickmorty.ui.theme.statusBarColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val allCharacters = homeViewModel.getAllCharacters.collectAsLazyPagingItems()
    val systemBarColor = MaterialTheme.colors.statusBarColor.toArgb()

    SideEffect { activity.window.statusBarColor = systemBarColor }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {}
            )
        },
        content = {
            ListContent(
                characters = allCharacters,
                navController = navController
            )
        }
    )
}