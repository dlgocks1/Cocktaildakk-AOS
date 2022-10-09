package com.compose.cocktaildakk_compose.ui.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  navController: NavHostController = rememberNavController(),
  splashViewModel: SplashViewModel = hiltViewModel()
) {

  LaunchedEffect(Unit) {
    splashViewModel.checkCocktailVersion(
      onStart = {
        Log.i("SplashScreen", "VersionCheck")
      },
      onEnd = {
        if (splashViewModel.isUserInfo == null) {
          navController.navigate("OnboardGraph") {
            popUpTo("splash") {
              inclusive = true
            }
          }
        } else {
          navController.navigate("MainGraph") {
            popUpTo("splash") {
              inclusive = true
            }
          }
        }
      })
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
  }
}

@Preview
@Composable
fun PreviewSplash() {
  SplashScreen()
}