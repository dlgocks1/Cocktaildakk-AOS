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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.CUSTOM_REC_TEXT
import com.compose.cocktaildakk_compose.KEYWORD_REC_TEXT
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    appState: ApplicationState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pages = listOf(
        CUSTOM_REC_TEXT,
        KEYWORD_REC_TEXT,
    )

    LaunchedEffect(key1 = Unit) {
        homeViewModel.initMainRec()
    }

    LaunchedEffect(key1 = homeViewModel.randomKeywordTag.value) {
        homeViewModel.getBaseKeywordRecList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd)
    ) {
        SearchButton {
            appState.navController.navigate("search")
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
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
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
                    navController = appState.navController,
                    mainRecList = homeViewModel.mainRecList.value
                )
                else -> KeywordRecScreen(
                    appState, homeViewModel
//                    navController = appState.navController,
//                    baseTagRecList = homeViewModel.baseTagRecList.value,
//                    keywordTagRecList = homeViewModel.keywordRecList.value,
//                    randomRecList = homeViewModel.randomRecList.value,
//                    randomBaseTag = homeViewModel.randomBaseTag,
//                    randomKeywordTag = homeViewModel.randomKeywordTag.value,
                )
            }
        }
    }
}





