package com.compose.cocktaildakk_compose.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.NetworkState
import com.compose.cocktaildakk_compose.ui.components.NetworkOfflineDialog
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MAIN_GRAPH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SPLASH
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
    splashViewModel: SplashViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val networkState by splashViewModel.networkState.collectAsState(initial = NetworkState.None)
    val scope = rememberCoroutineScope()

    LaunchedEffect(networkState) {
        if (networkState == NetworkState.Connected) {
            splashViewModel.checkCocktailVersion(
                onError = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                },
                onSuccess = {
                    if (splashViewModel.isUserInfo == null) navigateToOnboard(navController)
                    else navigateToMain(navController)
                })
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.img_splash_background),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_splash_logo),
                contentDescription = "Img Splash Logo", modifier = Modifier.fillMaxSize(0.6f)
            )
        }
        NetworkOfflineDialog(networkState = networkState)
    }
}

private fun navigateToOnboard(navController: NavHostController) {
    navController.navigate("OnboardGraph") {
        popUpTo(SPLASH) {
            inclusive = true
        }
    }
}

private fun navigateToMain(navController: NavHostController) {
    navController.navigate(MAIN_GRAPH) {
        popUpTo("splash") {
            inclusive = true
        }
    }
}

