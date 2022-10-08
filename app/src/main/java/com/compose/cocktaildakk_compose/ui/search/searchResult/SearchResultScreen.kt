@file:OptIn(ExperimentalMaterialApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.search.SearchViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchResultScreen(
  navController: NavController = rememberNavController(),
  searchViewModel: SearchViewModel = hiltViewModel(),
) {

  val listState: LazyListState =
    rememberLazyListState(searchViewModel.index, searchViewModel.offset)
  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = VISIBLE_SEARCH_STR.value) {
    withContext(Dispatchers.Default) {
      searchViewModel.getCocktails(
        VISIBLE_SEARCH_STR.value
      )
    }
    listState.scrollToItem(0)
  }

  LaunchedEffect(key1 = listState.isScrollInProgress) {
    if (!listState.isScrollInProgress) {
      searchViewModel.index = listState.firstVisibleItemIndex
      searchViewModel.offset = listState.firstVisibleItemScrollOffset
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    SearchButton(onclick = {
      navController.navigate("search")
    })
    Text(
      text = "총 ${searchViewModel.cocktailList.value.size}개의 검색 결과",
      fontSize = 16.sp,
      modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
      color = Color.White,
      fontWeight = FontWeight.Bold
    )
    LazyColumn(
      state = listState,
      modifier = Modifier.fillMaxSize()
    ) {
      items(searchViewModel.cocktailList.value, key = { it.idx }) { item ->
        SearchListItem(
          modifier = Modifier.clickable {
            navController.navigate("detail/${item.idx}")
          },
          cocktail = item,
          toggleBookmark = {
            scope.launch { searchViewModel.toggleBookmark(idx = item.idx) }
          }
        )
      }
    }
  }
}

@Composable
fun SearchListItem(modifier: Modifier, cocktail: Cocktail, toggleBookmark: () -> Unit = {}) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(120.dp)
      .padding(0.dp, 10.dp)
  ) {
    Surface(
      modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .padding(20.dp, 0.dp),
      color = Color.Transparent
    ) {
      Image(
        painter = painterResource(id = R.drawable.img_list_dummy),
        contentDescription = "Img List",
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .fillMaxHeight(),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
      )
    }
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxHeight(),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = cocktail.krName,
        fontSize = 18.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(5.dp))
      Text(text = cocktail.enName, fontSize = 14.sp, color = Color(0x60ffffff))
      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        cocktail.keyword.split(',').map {
          Surface(
            modifier = Modifier
              .border(BorderStroke(1.dp, Color_Cyan), RoundedCornerShape(10.dp))
              .clip(RoundedCornerShape(10.dp))
              .clickable {
                VISIBLE_SEARCH_STR.value = it.trim()
              },
            color = Color.Transparent
          ) {
            Text(
              text = it.trim(),
              fontSize = 12.sp,
              color = Color.White,
              modifier = Modifier.padding(10.dp, 3.dp)
            )
          }
        }
      }

    }
    Surface(
      modifier = Modifier
        .padding(top = 20.dp, end = 20.dp)
        .clickable {

        },
      color = Color.Transparent
    ) {
      Icon(
        modifier = Modifier
          .size(24.dp)
          .clickable {
            toggleBookmark?.invoke()
          },
        painter =
        if (cocktail.isBookmark) painterResource(id = R.drawable.ic_baseline_bookmark_24) else painterResource(
          R.drawable.ic_outline_bookmark_border_24
        ),
        contentDescription = "Icon Bookmark",
        tint = Color.White
      )
    }
  }
}


