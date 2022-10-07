package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
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
import com.compose.cocktaildakk_compose.ui.components.TagCheckbox
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardViewModel.TagList
import com.compose.cocktaildakk_compose.ui.theme.Color_LightGreen
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun OnboardKeywordScreen(navController: NavController = rememberNavController()) {


  val checkedState = remember {
    mutableStateListOf(
      TagList(text = "가벼운"),
      TagList(text = "독한"),
      TagList(text = "상쾌한"),
      TagList(text = "탄산"),
      TagList(text = "알록달록"),
      TagList(text = "레이디 킬러"),
      TagList(text = "트로피컬"),
      TagList(text = "과일"),
    )
  }

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
          text = "어떤 기주를\n선호하시나요?",
          fontSize = 36.sp,
          modifier = Modifier,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(50.dp))

      LazyColumn(
        modifier = Modifier
          .weight(0.7f)
          .padding(40.dp, 0.dp),
      ) {
        item {
          FlowRow(
            modifier = Modifier.fillMaxWidth(),
            crossAxisSpacing = 10.dp,
          ) {
            for (i in 0 until checkedState.size) {
              TagCheckbox(
                isChecked = checkedState[i].isSelected,
                onCheckChanged = {
                  checkedState[i] = checkedState[i].copy(isSelected = !checkedState[i].isSelected)
                },
                text = checkedState[i].text,
                modifier = Modifier
              )
            }
          }
        }
      }

      Surface(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .background(color = Color.Transparent)
          .offset(y = 20.dp)
          .clickable {
            navController.navigate("MainGraph")
          },
        color = Color.Transparent
      ) {
        Text(
          text = "시작하기",
          modifier = Modifier
            .border(
              color = Color_LightGreen,
              width = 1.dp,
              shape = CircleShape
            )
            .clip(CircleShape)
            .background(color = Color_LightGreen)
            .padding(20.dp, 10.dp),
          color = Color.Black
        )
      }
      Spacer(modifier = Modifier.fillMaxHeight(0.2f))
    }

  }
}

@Preview
@Composable
fun PreviewOnboardKeywordScreen() {
  OnboardKeywordScreen()
}