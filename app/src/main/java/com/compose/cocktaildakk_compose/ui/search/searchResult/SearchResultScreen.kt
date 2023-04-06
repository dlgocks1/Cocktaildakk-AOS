@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.bookmark.BookmarkViewModel
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.search.SearchResultViewModel
import com.compose.cocktaildakk_compose.ui.search.components.SearchResultTopView
import com.compose.cocktaildakk_compose.ui.search.components.SearchedCocktails
import com.compose.cocktaildakk_compose.ui.theme.*
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SEARCH

@Composable
fun SearchResultScreen(
    navController: NavController = rememberNavController(),
    searchResultViewModel: SearchResultViewModel,
) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    LaunchedEffect(key1 = VISIBLE_SEARCH_STR.value) {
        searchResultViewModel.getCocktails(
            VISIBLE_SEARCH_STR.value,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd),
    ) {
        SearchButton(
            onclick = {
                navController.navigate(SEARCH)
            }
        )
        SearchResultTopView(
            size = searchResultViewModel.cocktailList.size,
            reccomandOption = searchResultViewModel.sortByRecommand.value,
            changeRecommandOption = {
                searchResultViewModel.updateUserReccomandSortOption(it)
            }
        )
        SearchedCocktails(
            cocktailList = searchResultViewModel.cocktailList,
            navigateToDetail = {
                navController.navigate(ScreenRoot.DETAIL.format(it))
            },
            bookmarkList = bookmarkViewModel.bookmarkList.value,
            deleteBookmark = {
                bookmarkViewModel.deleteBookmark(it)
            },
            insertBookmark = {
                bookmarkViewModel.insertBookmark(it)
            }
        )
    }
}

