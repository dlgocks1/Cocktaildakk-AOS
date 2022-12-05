@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.compose.cocktaildakk_compose.NOTHING_SEARCHED_TEXT
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail

@Composable
fun ElasticSearchScreen(
    searchCocktailList: LazyPagingItems<Cocktail>,
    navController: NavController
) {

    LazyColumn(
        state = LazyListState(),
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (searchCocktailList.itemCount == 0) {
            item {
                Text(
                    text = NOTHING_SEARCHED_TEXT,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        itemsIndexed(searchCocktailList) { index, item ->
            item?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("detail/${item.idx}")
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_search_24),
                        contentDescription = "Icon Search"
                    )
                    Column(
                        modifier = Modifier.offset(x = 10.dp)
                    ) {
                        Text(text = item.krName, fontSize = 16.sp)
                        Text(text = item.enName, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                if (index < searchCocktailList.itemSnapshotList.items.lastIndex) {
                    Divider(
                        color = Color.White.copy(alpha = 0.2f),
                    )
                }
            }
        }
    }
}
