@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchResultScreen(
    navController: NavController = rememberNavController(),
    searchResultViewModel: SearchResultViewModel,
) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    LaunchedEffect(key1 = VISIBLE_SEARCH_STR.value) {
        searchResultViewModel.getCocktails(
            VISIBLE_SEARCH_STR.value
        )
        searchResultViewModel.listState.scrollToItem(0)
    }

    LaunchedEffect(Unit) {
//    listState = LazyListState(searchResultViewModel.index, searchResultViewModel.offset)
    }

    DisposableEffect(Unit) {
        onDispose {
//      searchResultViewModel.index = listState.firstVisibleItemIndex
//      searchResultViewModel.offset = listState.firstVisibleItemScrollOffset
        }
    }

//  LaunchedEffect(key1 = listState.isScrollInProgress) {
//    if (!listState.isScrollInProgress) {
//      searchResultViewModel.index = listState.firstVisibleItemIndex
//      searchResultViewModel.offset = listState.firstVisibleItemScrollOffset
//    }
//  }

    // 화면 내에서 SEARCH_STR이 바뀌었을 때 ex) 태그 클릭했을 때
//  LaunchedEffect(key1 = VISIBLE_SEARCH_STR.value) {
//    searchResultViewModel.getTotalCount(VISIBLE_SEARCH_STR.value)
//    searchResultViewModel.getCocktailPaging()
//    searchResultViewModel.listState.scrollToItem(0)
//  }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd)
    ) {
        SearchButton(onclick = {
            navController.navigate("search")
        })
        Text(
            text = if (searchResultViewModel.cocktailList.value.isEmpty()) "검색결과가 없습니다." else
                "총 ${searchResultViewModel.cocktailList.value.size}개의 검색 결과",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        ColumnList(
            searchResultViewModel,
            navController,
            bookmarkViewModel = bookmarkViewModel
        )
    }
}

@Composable
private fun ColumnList(
    searchResultViewModel: SearchResultViewModel,
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel,
) {

//  val cocktailList = searchResultViewModel.pagingCocktailList.collectAsLazyPagingItems()

//  when (cocktailList.itemCount) {
//    0 -> {
//      Log.i("test", "itemcount 0")
//      return
//    }
//    else -> {
    AnimatedVisibility(visible = searchResultViewModel.cocktailList.value.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
//      state = listState,
        ) {
//      Log.w(
//        "TEST", "List state recompose. " +
//            "first_visible=${searchResultViewModel.listState.firstVisibleItemIndex}, " +
//            "offset=${searchResultViewModel.listState.firstVisibleItemScrollOffset}, " +
//            "amount items=${cocktailList.itemCount}"
//      )
            items(searchResultViewModel.cocktailList.value, key = { it.idx }) { item ->
                SearchListItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("detail/${item.idx}")
                        }
                        .animateItemPlacement(),
                    cocktail = item,
                    bookmarkViewModel = bookmarkViewModel
                )
            }
//          items(cocktailList, key = { item -> item.idx }) { item ->
//            item?.let {
//              SearchListItem(
//                modifier = Modifier
//                  .clickable {
//                    navController.navigate("detail/${item.idx}") {
//                    }
//                  },
//                cocktail = item,
//                toggleBookmark = {
//                  scope.launch {
//                    searchResultViewModel.toggleBookmark(cocktail = item)
//                    cocktailList.refresh()
//                  }
//                }
//              )
//            }
//        }
        }
    }
}
//  }
//}


@Composable
fun SearchListItem(
    modifier: Modifier,
    cocktail: Cocktail,
    onRestore: () -> Unit = {},
    bookmarkViewModel: BookmarkViewModel
) {
    val isBookmarked =
        bookmarkViewModel.bookmarkList.value.contains(BookmarkIdx(idx = cocktail.idx))
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
//      Image(
//        painter = painterResource(id = R.drawable.img_list_dummy),
//        contentDescription = "Img List",
//        modifier = Modifier
//          .fillMaxWidth(0.5f)
//          .fillMaxHeight(),
//        alignment = Alignment.Center,
//        contentScale = ContentScale.Crop
//      )
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cocktail.listImgUrl)
                    .crossfade(true)
                    .build(),
                loading = {
                    ListCircularProgressIndicator(fraction = 0.5f)
                },
                contentDescription = stringResource(R.string.main_rec),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .background(Color_Default_Backgounrd),
                alignment = Alignment.Center,
                error = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                            contentDescription = "Icon Error",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                },
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
                        if (isBookmarked) {
                            bookmarkViewModel.deleteBookmark(cocktail.idx)
                        } else {
                            bookmarkViewModel.insertBookmark(cocktail.idx)
                        }
                        onRestore()
                    },
                painter =
                if (isBookmarked) painterResource(id = R.drawable.ic_baseline_bookmark_24) else painterResource(
                    R.drawable.ic_outline_bookmark_border_24
                ),
                contentDescription = "Icon Bookmark",
                tint = Color.White
            )
        }
    }
}