package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchItem(
    modifier: Modifier,
    cocktail: Cocktail,
    onRestore: () -> Unit = {},
    bookmarkList: List<BookmarkIdx>,
    deleteBookmark: (Int) -> Unit,
    insertBookmark: (Int) -> Unit,
) {
    val isBookmarked = bookmarkList.contains(BookmarkIdx(idx = cocktail.idx))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(0.dp, 10.dp),
    ) {
        Surface(
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(20.dp, 0.dp),
            color = Color.Transparent,
        ) {
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
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                            contentDescription = "Icon Error",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White,
                        )
                    }
                },
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = cocktail.krName,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = cocktail.enName, fontSize = 14.sp, color = Color(0x60ffffff))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                cocktail.keyword.split(',').map {
                    Surface(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color_Cyan), RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                SingletonObject.VISIBLE_SEARCH_STR.value = it.trim()
                            },
                        color = Color.Transparent,
                    ) {
                        Text(
                            text = it.trim(),
                            fontSize = 12.sp,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp, 3.dp),
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
            color = Color.Transparent,
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (isBookmarked) {
                            deleteBookmark(cocktail.idx)
                        } else {
                            insertBookmark(cocktail.idx)
                        }
                        onRestore()
                    },
                painter =
                if (isBookmarked) {
                    painterResource(id = R.drawable.ic_baseline_bookmark_24)
                } else {
                    painterResource(
                        R.drawable.ic_outline_bookmark_border_24,
                    )
                },
                contentDescription = "Icon Bookmark",
                tint = Color.White,
            )
        }
    }
}
