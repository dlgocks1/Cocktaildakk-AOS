@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchItem

@Composable
fun SearchedCocktails(
    navigateToDetail: (Int) -> Unit,
    cocktailList: List<Cocktail>,
    bookmarkList: List<BookmarkIdx>,
    deleteBookmark: (Int) -> Unit,
    insertBookmark: (Int) -> Unit,
) {
    AnimatedVisibility(visible = cocktailList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            items(cocktailList, key = { it.idx }) { item ->
                SearchItem(
                    modifier = Modifier
                        .clickable {
                            navigateToDetail(item.idx)
                        }
                        .animateItemPlacement(),
                    cocktail = item,
                    bookmarkList = bookmarkList,
                    deleteBookmark = deleteBookmark,
                    insertBookmark = insertBookmark,
                )
            }
        }
    }
}