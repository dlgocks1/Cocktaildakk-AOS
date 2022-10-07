package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun OnboardStartScreen(navController: NavController = rememberNavController()) {
  ImageWithBackground(
    modifier = Modifier
      .fillMaxSize()
      .blur(20.dp),
    backgroundDrawableResId = R.drawable.img_onboard_back,
    contentDescription = "Img Onboard Back"
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize(),
    ) {
      Column(
        modifier = Modifier
          .padding(40.dp)
          .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
      ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "취향 키워드\n설정하기", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(text = "취향을 설정하면\n당신이 좋아할 만한 칵테일을\n추천해 드려요.", fontSize = 22.sp)
        Surface(
          modifier = Modifier
            .background(color = Color.Transparent)
            .clickable {
              navController.navigate("onboard_age")
            },
          color = Color.Transparent
        ) {
          Text(
            text = "시작하기", modifier = Modifier
              .border(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                width = 1.dp,
                shape = CircleShape
              )
              .padding(20.dp, 10.dp)
          )
        }
        Text(
          text = "건너뛰기", modifier = Modifier
            .offset(x = 20.dp)
            .clickable {
              navController.navigate("MainGraph")
            }
        )
        Spacer(modifier = Modifier.weight(2f))
      }
    }
  }

}

@Preview
@Composable
fun PreviewOnboardStartScreen() {
  OnboardStartScreen()
}

