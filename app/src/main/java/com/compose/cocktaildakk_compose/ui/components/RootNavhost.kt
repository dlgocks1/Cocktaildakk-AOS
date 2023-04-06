package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.compose.cocktaildakk_compose.ui.detail.gallery.GalleryScreen
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.navigation.detailGraph
import com.compose.cocktaildakk_compose.ui.navigation.mainGraph
import com.compose.cocktaildakk_compose.ui.navigation.onboardGraph
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardStartScreen
import com.compose.cocktaildakk_compose.ui.search.SearchScreen
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchResultViewModel
import com.compose.cocktaildakk_compose.ui.splash.SplashScreen
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot

@Composable
fun RootNavhost(
    appState: ApplicationState,
) {
    val searchResultViewModel: SearchResultViewModel = hiltViewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = appState.scaffoldState,
        bottomBar = { BottomBar(appState) },
    ) { innerPadding ->
        NavHost(
            appState.navController,
            startDestination = ScreenRoot.SPLASH,
            Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent),
        ) {
            composable(ScreenRoot.SPLASH) {
                SplashScreen(appState)
            }
            composable(ScreenRoot.ONBOARD_START) {
                OnboardStartScreen(appState.navController)
            }
            onboardGraph(appState)
            mainGraph(appState, searchResultViewModel)
            detailGraph(appState)
            composable(ScreenRoot.SEARCH) {
                SearchScreen(appState)
            }
            composable(ScreenRoot.GALLERY) {
                GalleryScreen(appState = appState)
            }
        }
    }
}