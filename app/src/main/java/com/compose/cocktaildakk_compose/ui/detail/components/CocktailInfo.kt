package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.bookmark.BookmarkViewModel
import com.compose.cocktaildakk_compose.ui.components.TagButton
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CoktailInfo(cocktail: Cocktail) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val isBookmarked =
        bookmarkViewModel.bookmarkList.value.contains(BookmarkIdx(idx = cocktail.idx))

    Column(
        modifier = Modifier.padding(20.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = LEVEL_TEXT,
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "약 ${cocktail.level}$LEVEL_UNIT_TEXT",
                    fontSize = 16.sp,
                    modifier = Modifier.width(60.dp),
                )
            }
            Icon(
                painter = painterResource(
                    id = if (isBookmarked) {
                        R.drawable.ic_baseline_bookmark_24
                    } else {
                        R.drawable.ic_outline_bookmark_border_24
                    },
                ),
                contentDescription = null,
                modifier = Modifier.clickable {
                    if (isBookmarked) {
                        bookmarkViewModel.deleteBookmark(cocktail.idx)
                    } else {
                        bookmarkViewModel.insertBookmark(cocktail.idx)
                    }
                },
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = MIXING_TEXT,
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = cocktail.mix,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = BASE_TEXT,
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = cocktail.base,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row {
            Text(
                text = KEYWORD_TEXT,
                fontSize = 18.sp,
                modifier = Modifier.width(60.dp),
                fontWeight = FontWeight.Bold,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                crossAxisSpacing = 10.dp,
            ) {
                val tag = cocktail.keyword.split(',')
                tag.indices.forEach { i ->
                    TagButton(tag[i])
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        Column {
            Text(
                text = COCKTAIL_EXPLAIN_TEXT,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = cocktail.explain,
            )
        }
        Row {
            Text(
                fontSize = 18.sp,
                text = INGRADIENT_TEXT,
                modifier = Modifier.weight(0.3f),
                fontWeight = FontWeight.Bold,
            )
            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                cocktail.ingredient.split(',').map {
                    Text(text = it.trim())
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}