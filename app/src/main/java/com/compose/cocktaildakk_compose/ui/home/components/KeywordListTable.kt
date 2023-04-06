package com.compose.cocktaildakk_compose.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.INFO_TAG_COCKTAIL_TEXT
import com.compose.cocktaildakk_compose.MORE_INFO_TEXT
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.components.CocktailListImage
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan

@Composable
fun CocktailWithKeyword(
    navigateToSearchTab: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    cocktailList: List<Cocktail>,
    tagName: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color_Cyan),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "#$tagName $INFO_TAG_COCKTAIL_TEXT",
                    fontSize = 16.sp,
                )
                Row(
                    modifier = Modifier.clickable {
                        SingletonObject.VISIBLE_SEARCH_STR.value = tagName
                        navigateToSearchTab()
                    },
                ) {
                    Text(text = MORE_INFO_TEXT, fontSize = 12.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                        contentDescription = "More Info Btn",
                        modifier = Modifier.size(12.dp),
                    )
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            ) {
                items(cocktailList) { item ->
                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .clickable {
                                navigateToDetail(item.idx)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CocktailListImage(item)
                        Text(
                            text = item.krName,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp, 0.dp),
                        )
                        Text(
                            text = item.enName,
                            fontSize = 10.sp,
                            color = Color(0xff5E5E5E),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}