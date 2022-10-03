package com.compose.cocktaildakk_compose.ui.Search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme

@Composable
fun SearchScreen(navController: NavHostController) {

  val focusRequest = remember {
    FocusRequester()
  }
  val searchValue = remember {
    mutableStateOf("")
  }

  LaunchedEffect(Unit) {
    focusRequest.requestFocus()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd),
  ) {
    Row(
      modifier = Modifier.padding(20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
          contentDescription = "Icon Back Arrow",
          modifier = Modifier
            .padding(10.dp)
            .clickable {
              navController.popBackStack()
            },
        )
      }

      TextField(
        value = searchValue.value,
        onValueChange = { searchValue.value = it },
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(color = Color.White)
          .height(40.dp)
          .focusRequester(focusRequest),
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp),
//        label = {
//          Text(text = "검색어를 입력해주세요.", fontSize = 14.sp, color = Color.White)
//        },
        colors = TextFieldDefaults.textFieldColors(
          cursorColor = Color.Black,
          textColor = Color.Black,
//          focusedIndicatorColor = Color.Transparent, //hide the indicator
//          unfocusedIndicatorColor = Color.Transparent
        )
      )
    }

    RecentSearch()

    Spacer(
      modifier = Modifier
        .height(5.dp)
        .fillMaxWidth()
        .background(color = Color(0x40ffffff))
    )
    Text(
      text = "나에게 맞는 칵테일 추천 받기",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      color = Color.White,
      modifier = Modifier.padding(20.dp)
    )
    Column(
      modifier = Modifier.padding(20.dp, 10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      for (i in 0..2) {
        Text(text = "칵테일 추천 $i", fontSize = 16.sp, color = Color.White)
      }
    }
  }
}

@Composable
private fun RecentSearch() {
  val scrollState = rememberScrollState()
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp, 0.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = "최근 검색어", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Button(onClick = { /*TODO*/ }) {
      Text(text = "전체 삭제", fontSize = 12.sp, color = Color.White)
    }
  }
  Row(
    modifier = Modifier
      .padding(20.dp, 0.dp)
      .horizontalScroll(state = scrollState),
    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    for (i in 0..5) {
      Text(text = "최근 검색어 $i", color = Color.White)
    }
  }
  Spacer(modifier = Modifier.height(15.dp))
}

@Preview
@Composable
fun PreviewSearchView() {
  SearchScreen(rememberNavController())
}