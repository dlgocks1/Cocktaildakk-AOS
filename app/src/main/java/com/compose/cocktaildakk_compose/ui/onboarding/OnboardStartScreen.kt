package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import kotlinx.coroutines.launch

@Composable
fun OnboardStartScreen(
  navController: NavController = rememberNavController(),
  onboardViewModel: OnboardViewModel = hiltViewModel()
) {

  val scope = rememberCoroutineScope()
  var skipDialog = remember { mutableStateOf(false) }

  ImageWithBackground(
    modifier = Modifier
      .fillMaxSize()
      .blur(20.dp),
    backgroundDrawableResId = R.drawable.img_onboard_back,
    contentDescription = "Img Onboard Back"
  ) {
    Box {
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
                navController.navigate("onboard_nickname")
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
                skipDialog.value = true

              }
          )
          Spacer(modifier = Modifier.weight(2f))
        }
      }
      if (skipDialog.value) {
        SkipDialog(onDismiss = {
          skipDialog.value = !skipDialog.value
        }, onClick = {
          skipDialog.value = !skipDialog.value
          scope.launch {
            onboardViewModel.insertUserinfo()
          }
          navController.navigate("MainGraph") {
            popUpTo("OnBoardGraph") {
              inclusive = true
            }
          }
        })
      }
    }
  }
}

@Composable
fun SkipDialog(onClick: () -> Unit, onDismiss: () -> Unit) {

  Dialog(onDismissRequest = { onDismiss() }) {
    Card(
      elevation = 8.dp,
      shape = RoundedCornerShape(12.dp)
    ) {
      Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_info_24),
          contentDescription = "Icon Info",
          tint = Color.White,
          modifier = Modifier
            .padding(0.dp, 10.dp)
            .size(24.dp)
        )
        Text(
          text = "건너뛰시면\n정보가 기본값으로 설정됩니다.",
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          modifier = Modifier.padding(8.dp),
          textAlign = TextAlign.Center
        )
        // Buttons
        Row(
          horizontalArrangement = Arrangement.End,
          modifier = Modifier.fillMaxWidth()
        ) {
          TextButton(onClick = { onDismiss() }) {
            Text(text = "취소", color = Color.White)
          }
          Spacer(modifier = Modifier.width(4.dp))
          TextButton(onClick = {
            onClick()
          }) {
            Text(text = "확인", color = Color.White)
          }
        }
      }
    }
  }

}

@Preview
@Composable
fun PreviewOnboardStartScreen() {
  OnboardStartScreen()
}

