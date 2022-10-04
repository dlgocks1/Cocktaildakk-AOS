@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.CustomTextField
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme

@Composable
fun SearchScreen(
  navController: NavHostController = rememberNavController(),
  searchViewModel: SearchViewModel,
) {

  val focusManager = LocalFocusManager.current
  val focusRequest = remember {
    FocusRequester()
  }
  var textFieldValue = remember {
    val initValue = searchViewModel.searchStrResult.value
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
              focusManager.clearFocus()
              navController.popBackStack()
            },
        )
      }

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
        value = textFieldValue.value,
        onvalueChanged = { textFieldValue.value = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
          val text = textFieldValue.value.text
          searchViewModel.addSearchStr(text)
          onSearch(searchViewModel, text, focusManager, navController)
        }),
      )
    }
    RecentSearch(
      searchViewModel = searchViewModel,
      focusManager = focusManager,
      navController = navController
    )
    Spacer(
      modifier = Modifier
        .height(5.dp)
        .fillMaxWidth()
        .background(color = Color(0x40ffffff))
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp, 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "나에게 맞는 칵테일 추천 받기",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
      )
      Button(
        onClick = {
          focusManager.clearFocus()
          navigateToMainGraph(destination = "home", navController = navController)
        },
      ) {
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0x30ffffff))
            .padding(15.dp, 3.dp),
        ) {
          Text(text = "더보기", fontSize = 12.sp, color = Color.White)
          Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
            contentDescription = "Icon More",
            modifier = Modifier
              .size(16.dp)
              .offset(x = 5.dp),
            tint = Color(0xffffffff),
          )
        }
      }
    }

    Column(
      modifier = Modifier.padding(40.dp, 0.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      for (i in 0..2) {
        Text(text = "칵테일 추천 $i", fontSize = 16.sp, color = Color.White)
      }
    }
  }
}

private fun onSearch(
  searchViewModel: SearchViewModel,
  textFieldValue: String,
  focusManager: FocusManager,
  navController: NavHostController
) {
  searchViewModel.handleUpdateSearchResult(textFieldValue)
  focusManager.clearFocus()
  navigateToMainGraph(destination = "searchresult", navController = navController)
}

private fun navigateToMainGraph(
  destination: String,
  navController: NavHostController
) {
  navController.navigate(destination) {
    popUpTo("MainGraph") {
      inclusive = true
    }
  }
}

@Composable
private fun RecentSearch(
  searchViewModel: SearchViewModel,
  focusManager: FocusManager,
  navController: NavHostController,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp, 0.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = "최근 검색어", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Button(
      onClick = {
        searchViewModel.removeAllSearchStr()
      },
    ) {
      Text(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(color = Color(0x30ffffff))
          .padding(15.dp, 3.dp),
        text = "전체 삭제", fontSize = 12.sp, color = Color.White
      )
    }
  }

  LazyRow(
    modifier = Modifier
      .padding(20.dp, 10.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    items(searchViewModel._recentSearchList.value, key = { it.id }) {
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .border(1.dp, Color_Cyan, RoundedCornerShape(10.dp))
          .padding(10.dp, 0.dp)
          .animateItemPlacement(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = it.value,
          color = Color.White,
          modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clickable {
              onSearch(searchViewModel, it.value, focusManager, navController)
            }
        )
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_close_24),
          contentDescription = "Icon Delete",
          tint = Color_Cyan,
          modifier = Modifier
            .offset(x = 3.dp)
            .clickable {
              searchViewModel.removeSearchStr(it.id)
            }
        )
      }
    }
  }

  if (searchViewModel._recentSearchList.value.isEmpty())
    Text(
      text = "최근 검색어가 없습니다.",
      modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold
    )
  Spacer(modifier = Modifier.height(10.dp))
}

//@Preview
//@Composable
//fun PreviewSearchView() {
//  SearchScreen(updateSearchStr = { str ->
//    searchViewModel.handleUpdateSearchStr(str)
//  })
//}