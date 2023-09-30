package com.dfavilav.zararickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.dfavilav.zararickmorty.domain.use_cases.UseCases
import com.dfavilav.zararickmorty.navigation.SetupNavGraph
import com.dfavilav.zararickmorty.ui.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @Inject
    lateinit var useCases: UseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
