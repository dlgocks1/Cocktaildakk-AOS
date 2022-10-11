package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ListCircularProgressIndicator(fraction: Float = 0.5f) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.Transparent),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    CircularProgressIndicator(
      color = Color.White,
      modifier = Modifier.fillMaxSize(fraction)
    )
  }
}


