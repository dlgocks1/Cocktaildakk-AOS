@file:OptIn(ExperimentalAnimationApi::class)

package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70


@Composable
fun ReviewUploadAnimatedProgressBar(loadingState: Int) {
    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color_Default_Backgounrd_70)
            .clickable {
                // Do Nothing!
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(targetState = loadingState) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                progress = loadingState.toFloat() / 100f,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedContent(targetState = loadingState) {
            Text(text = "업로드 중...", fontSize = 16.sp, color = Color.White)
        }
    }
}


