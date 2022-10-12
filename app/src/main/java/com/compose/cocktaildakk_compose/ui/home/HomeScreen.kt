@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
  navController: NavController = rememberNavController(),
  homeViewModel: HomeViewModel = hiltViewModel()
) {
  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState()
  val pages = listOf(
    "맞춤 추천",
    "키워드 추천",
  )
  LaunchedEffect(Unit) {
    with(homeViewModel) {
      getBaseKeywordRecList()
      getMainRecList()
      getBaseTagRecList()
      getRandomRecList()
    }
  }
  LaunchedEffect(key1 = homeViewModel.randomKeywordTag.value) {
    homeViewModel.getBaseKeywordRecList()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    SearchButton(
      onclick = {
        navController.navigate("search")
      }
    )
    TabRow(
      selectedTabIndex = pagerState.currentPage,
      indicator = { tabPositions ->
        TabRowDefaults.Indicator(
          Modifier
            .fillMaxWidth()
            .pagerTabIndicatorOffset(pagerState, tabPositions)
        )
      },
      modifier = Modifier.padding(20.dp, 0.dp),
      backgroundColor = Color.Transparent
    ) {
      CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        pages.forEachIndexed { index, title ->
          Tab(
            text = { Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold) },
            selected = pagerState.currentPage == index,
            onClick = {
              scope.launch {
                pagerState.animateScrollToPage(index)
              }
            },
          )
        }
      }
    }
    HorizontalPager(
      count = 2, state = pagerState, modifier = Modifier.fillMaxSize(),
    ) { page ->
      when (page) {
        0 -> ReccomendScreen(
          navController = navController,
          mainRecList = homeViewModel.mainRecList.value
        )
        else -> KeywordRecScreen(
          navController = navController,
          baseTagRecList = homeViewModel.baseTagRecList.value,
          keywordTagRecList = homeViewModel.keywordRecList.value,
          randomRecList = homeViewModel.randomRecList.value,
          randomBaseTag = homeViewModel.randomBaseTag,
          randomKeywordTag = homeViewModel.randomKeywordTag.value,
        )
      }
    }
  }
}


@Preview
@Composable
fun HomePreview() {
  val navController = rememberNavController()
  HomeScreen(navController)
}



