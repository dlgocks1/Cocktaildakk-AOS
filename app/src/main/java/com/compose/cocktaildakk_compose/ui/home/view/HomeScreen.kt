@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.CUSTOM_REC_TEXT
import com.compose.cocktaildakk_compose.KEYWORD_REC_TEXT
import com.compose.cocktaildakk_compose.ui.Screen
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.domain.rememberApplicationState
import com.compose.cocktaildakk_compose.ui.home.HomeViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    appState: ApplicationState = rememberApplicationState(),
    homeViewModel: HomeViewModel = hiltViewModel(),
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
        homeViewModel.getBaseKeywordRecCocktails()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd),
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
                        .pagerTabIndicatorOffset(pagerState, tabPositions),
                )
            },
            modifier = Modifier.padding(20.dp, 0.dp),
            backgroundColor = Color.Transparent,
        ) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
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
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            when (page) {
                0 -> ReccomendScreen(
                    navigateToDetail = { idx ->
                        appState.navController.navigate(ScreenRoot.DETAIL.format(idx))
                    },
                    mainRecList = homeViewModel.mainRecCocktails.value,
                )
                else -> KeywordRecScreen(
                    navigateToDetail = { idx ->
                        appState.navController.navigate(ScreenRoot.DETAIL.format(idx))
                    },
                    navigateToSearchTab = {
                        appState.navController.navigate(ScreenRoot.SEARCH_RESULT) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    },
                    baseTagRecCocktails = homeViewModel.baseTagRecCocktails.value,
                    keywordTagRecCocktails = homeViewModel.keywordRecCocktails.value,
                    dailyRandomRecCocktails = homeViewModel.dailyRandomRecCocktails.value,
                    randomBaseTag = homeViewModel.randomBaseTag,
                    randomKeywordTag = homeViewModel.randomKeywordTag.value,
                )
            }
        }
    }
}
