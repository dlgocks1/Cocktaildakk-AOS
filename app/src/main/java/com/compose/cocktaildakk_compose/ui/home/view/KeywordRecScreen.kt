@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.home.components.CocktailWithKeyword
import com.compose.cocktaildakk_compose.ui.home.components.TodayRecCocktails
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun KeywordRecScreen(
    navigateToDetail: (Int) -> Unit,
    navigateToSearchTab: () -> Unit,
    dailyRandomRecCocktails: List<Cocktail>,
    baseTagRecCocktails: List<Cocktail>,
    keywordTagRecCocktails: List<Cocktail>,
    randomBaseTag: String,
    randomKeywordTag: String,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
    ) {
        Text(
            text = RANDOM_COCKTAIL,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp, 20.dp),
        )
        TodayRecCocktails(
            navigateToDetail = navigateToDetail,
            randomRecList = dailyRandomRecCocktails,
        )
        Text(
            text = INFO_REC_COCKTAIL_TEXT,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp, 20.dp),
        )
        CocktailWithKeyword(
            navigateToDetail = navigateToDetail,
            navigateToSearchTab = navigateToSearchTab,
            cocktailList = baseTagRecCocktails,
            tagName = randomBaseTag,
        )
        CocktailWithKeyword(
            navigateToDetail = navigateToDetail,
            navigateToSearchTab = navigateToSearchTab,
            cocktailList = keywordTagRecCocktails,
            tagName = randomKeywordTag,
        )
    }
}