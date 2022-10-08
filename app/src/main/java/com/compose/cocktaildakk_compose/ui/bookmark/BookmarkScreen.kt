@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.bookmark

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
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
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchListItem
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.swipeToDismiss
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
  navController: NavController = rememberNavController(),
  bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
  scaffoldState: ScaffoldState = rememberScaffoldState()
) {
  val scope = rememberCoroutineScope()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    Text(
      text = "내 보관함",
      fontSize = 16.sp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 20.dp), textAlign = TextAlign.Center,
      color = Color.White,
      fontWeight = FontWeight.Bold
    )

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
    ) {
      items(bookmarkViewModel.cocktailList.value, key = { item: Cocktail -> item.idx }) { item ->
        SearchListItem(
          modifier = Modifier
            .swipeToDismiss {
              scope.launch {
                bookmarkViewModel.toggleBookmark(item.idx)
                val result = scaffoldState.snackbarHostState.showSnackbar(
                  message = "북마크를 삭제했습니다.",
                  actionLabel = "취소"
                )
                if (result == SnackbarResult.ActionPerformed) {
                  bookmarkViewModel.restoreCocktail()
                }
              }
            }
            .animateItemPlacement(),
          cocktail = item,
          toggleBookmark = {
            scope.launch {
              bookmarkViewModel.toggleBookmark(item.idx)
              val result = scaffoldState.snackbarHostState.showSnackbar(
                message = "북마크를 삭제했습니다.",
                actionLabel = "취소"
              )
              if (result == SnackbarResult.ActionPerformed) {
                bookmarkViewModel.restoreCocktail()
              }
            }
          }
        )
      }
    }
  }
}
