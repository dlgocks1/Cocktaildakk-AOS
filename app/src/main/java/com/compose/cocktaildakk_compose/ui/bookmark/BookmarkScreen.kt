@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.CANCEL_TEXT
import com.compose.cocktaildakk_compose.DELETE_BOOKMARK_TEXT
import com.compose.cocktaildakk_compose.MY_BOOKMARK_TEXT
import com.compose.cocktaildakk_compose.NO_BOOKMARK_TEXT
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchListItem
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
    navController: NavController = rememberNavController(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val scope = rememberCoroutineScope()
    val bookmarkedCocktails = bookmarkViewModel.cocktailList.value.filter {
        bookmarkViewModel.bookmarkList.value.contains(BookmarkIdx(idx = it.idx))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd)
    ) {
        Text(
            text = MY_BOOKMARK_TEXT,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp), textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        if (bookmarkedCocktails.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = NO_BOOKMARK_TEXT, fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(bookmarkedCocktails, key = { item: Cocktail -> item.idx }) { item ->
                    SearchListItem(
                        modifier = Modifier
//              .swipeToDismiss(
//                onClicked = {
//                  navController.navigate("detail/${item.idx}")
//                },
//                onDismissed = {
//                  scope.launch {
//                    bookmarkViewModel.deleteBookmark(item.idx)
//                    val result = scaffoldState.snackbarHostState.showSnackbar(
//                      message = "북마크를 삭제했습니다.",
//                      actionLabel = "취소"
//                    )
//                    if (result == SnackbarResult.ActionPerformed) {
//                      bookmarkViewModel.restoreCocktail()
//                    }
//                  }
//                })
                            .clickable {
                                navController.navigate("detail/${item.idx}")
                            }
                            .animateItemPlacement(),
                        cocktail = item,
                        onRestore = {
                            scope.launch {
                                bookmarkViewModel.deleteBookmark(item.idx)
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = DELETE_BOOKMARK_TEXT,
                                    actionLabel = CANCEL_TEXT
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    bookmarkViewModel.restoreCocktail()
                                }
                            }
                        },
                        bookmarkViewModel = bookmarkViewModel
                    )
                }
            }
        }
    }
}
