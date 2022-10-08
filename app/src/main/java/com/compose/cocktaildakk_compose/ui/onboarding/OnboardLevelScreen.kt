package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan

@Composable
fun OnboardLevelScreen(
  navController: NavController = rememberNavController(),
  onboardViewModel: OnboardViewModel = hiltViewModel()
) {

  var sliderPosition = remember { mutableStateOf(5f) }

  ImageWithBackground(
    modifier = Modifier
      .fillMaxSize()
      .blur(20.dp),
    backgroundDrawableResId = R.drawable.img_onboard_back,
    contentDescription = "Img Onboard Back"
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      Column(
        modifier = Modifier
          .fillMaxHeight(0.3f)
          .padding(40.dp, 0.dp)
      ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = "선호 도수를\n알려 주세요.",
          fontSize = 36.sp,
          modifier = Modifier,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(50.dp))

      Column(
        modifier = Modifier
          .weight(0.7f)
          .padding(40.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        Text(text = "주로 마시는 술의 도수", fontSize = 20.sp)
        Slider(
          value = sliderPosition.value,
          onValueChange = { sliderPosition.value = it },
          valueRange = 5f..35f,
          onValueChangeFinished = {
            // viewModel.updateSelectedSliderValue(sliderPosition)
          },
          steps = 5,
          colors = SliderDefaults.colors(
            thumbColor = Color_Cyan,
            activeTrackColor = Color_Cyan
          )
        )
        Text(
          modifier = Modifier.align(Alignment.CenterHorizontally),
          text = if (sliderPosition.value.toInt() <= 5) "5도 미만"
          else if (sliderPosition.value.toInt() >= 35) "35도 이상"
          else "${sliderPosition.value.toInt()} 도",
          fontSize = 17.sp
        )
      }

      Surface(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .background(color = Color.Transparent)
          .clickable {
            onboardViewModel.level = sliderPosition.value.toInt()
            navController.navigate("onboard_base")
          },
        color = Color.Transparent
      ) {
        Text(
          text = "다음",
          modifier = Modifier
            .border(
              brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
              width = 1.dp,
              shape = CircleShape
            )
            .padding(20.dp, 10.dp),
        )
      }
      Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    }

  }
}


@Preview
@Composable
fun PreviewOnboardLevelScreen() {
  OnboardLevelScreen()
}