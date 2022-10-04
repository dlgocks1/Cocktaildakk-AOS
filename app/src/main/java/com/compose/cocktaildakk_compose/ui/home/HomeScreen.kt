@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState()
  val pages = listOf(
    "맞춤 추천",
    "키워드 추천",
  )
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    SearchButton {
      navController.navigate("search")
    }
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
      count = 2, state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
      when (page) {
        0 -> ReccomendScreen()
        else -> KeywordRecScreen()
      }
    }
  }
}

@Composable
fun KeywordRecScreen() {
  val scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(state = scrollState)
  ) {
    Text(
      text = "오늘의 칵테일",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(20.dp, 20.dp)
    )
    TodayRecTable()
    Text(
      text = "이런 칵테일 어때요?",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(20.dp, 20.dp)
    )
    KeywordListTable()
    KeywordListTable()
  }
}

@Composable
private fun TodayRecTable() {
  val scrollState = rememberScrollState()
  HorizontalPager(
    count = 5,
    modifier = Modifier
      .fillMaxWidth()
      .height(200.dp),
  ) { item ->
    Box {
      Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.img_main_dummy),
        contentDescription = "Today Rec Img",
        contentScale = ContentScale.Crop
      )
      Surface(
        modifier = Modifier
          .padding(10.dp)
          .align(Alignment.BottomEnd),
        color = Color.Transparent
      ) {
        Text(
          text = "$item", color = Color.White,
          modifier = Modifier
            .background(color = Color(0x30ffffff))
            .padding(10.dp, 2.dp)
        )
      }

    }
  }
}

@Composable
fun KeywordListTable() {
  val scrollState = rememberScrollState()
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
    color = Color.Transparent,
    shape = RoundedCornerShape(15.dp),
    border = BorderStroke(1.dp, Color_Cyan)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 20.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp, 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "#보드카가 들어간 칵테일", fontSize = 16.sp,
        )
        Row(modifier = Modifier.clickable {
        }) {
          Text(text = "더보기", fontSize = 12.sp)
          Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
            contentDescription = "More Info Btn",
            modifier = Modifier.size(12.dp)
          )
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(state = scrollState)
      ) {
        Spacer(modifier = Modifier.width(20.dp))
        repeat(5) {
          Column(modifier = Modifier.width(100.dp)) {
            Image(
              painter = painterResource(id = R.drawable.img_list_dummy),
              contentDescription = "Img List Dummy",
              modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(100.dp)
                .padding(bottom = 10.dp)
            )
            Text(text = "한글 이름", fontSize = 15.sp)
            Text(
              text = "영어 이름", fontSize = 10.sp, color = Color(0xff5E5E5E)
            )
          }
        }
      }
      Spacer(modifier = Modifier.width(20.dp))

    }
  }
}

@Preview
@Composable
fun HomePreview() {
  val navController = rememberNavController()
  HomeScreen(navController)
}



