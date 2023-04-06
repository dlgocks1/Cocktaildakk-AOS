package com.compose.cocktaildakk_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.compose.cocktaildakk_compose.ui.components.RootNavhost
import com.compose.cocktaildakk_compose.ui.domain.ManageBottomBarState
import com.compose.cocktaildakk_compose.ui.domain.rememberApplicationState
import com.compose.cocktaildakk_compose.ui.theme.CocktailDakkComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailDakkComposeTheme {
                val appState = rememberApplicationState()
                val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                ManageBottomBarState(navBackStackEntry, appState.bottomBarState)
                RootNavhost(appState)
            }
        }
    }
}

