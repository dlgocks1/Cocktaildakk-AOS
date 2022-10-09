package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.utils.CustomTextField
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun OnboardNicknameScreen(
  navController: NavController = rememberNavController(),
  onboardViewModel: OnboardViewModel = hiltViewModel(),
  scaffoldState: ScaffoldState
) {
  val scope = rememberCoroutineScope()
  val focusRequest = remember {
    FocusRequester()
  }
  var textFieldValue = remember {
    val initValue = ""
    val textFieldValue =
      TextFieldValue(
        text = initValue,
        selection = TextRange(initValue.length)
      )
    mutableStateOf(textFieldValue)
  }

  LaunchedEffect(Unit) {
    focusRequest.requestFocus()
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
          .fillMaxHeight(0.4f)
          .padding(40.dp, 0.dp)
      ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = "사용할\n" +
              "닉네임을\n" +
              "알려주세요.",
          fontSize = 36.sp,
          modifier = Modifier,
          fontWeight = FontWeight.Bold
        )
      }
      Spacer(modifier = Modifier.height(50.dp))

      Column(
        modifier = Modifier
          .weight(0.6f)
          .padding(40.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        CustomTextField(
          trailingIcon = null,
          modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
              color = Color.White,
              shape = RoundedCornerShape(20.dp)
            ),
          focusRequest = focusRequest,
          fontSize = 16.sp,
          placeholderText = "닉네임을 입력해주세요",
          value = textFieldValue.value,
          onvalueChanged = {
            if (it.text.length <= 10) {
              textFieldValue.value = it
            }
          },
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
          keyboardActions = KeyboardActions(onDone = {
            navigateNext(
              onboardViewModel = onboardViewModel,
              textFieldValue = textFieldValue,
              navController = navController
            ) {
              scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("3글자 이상 10글자 이하로 입력해주세요.")
              }
            }
          }),
        )
        Text(text = "3글자 이상 10글자 이하", fontSize = 14.sp)
      }

      Surface(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .background(color = Color.Transparent)
          .clickable {
            navigateNext(
              onboardViewModel = onboardViewModel,
              textFieldValue = textFieldValue,
              navController = navController
            ) {
              scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("3글자 이상 10글자 이하로 입력해주세요.")
              }
            }
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

private fun navigateNext(
  onboardViewModel: OnboardViewModel,
  textFieldValue: MutableState<TextFieldValue>,
  navController: NavController,
  makeSnackbar: () -> Unit
) {
  if (textFieldValue.value.text.length in 3..10) {
    onboardViewModel.nickname = textFieldValue.value.text
    navController.navigate("onboard_age")
  } else {
    makeSnackbar()
  }

}


