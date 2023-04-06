@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.CHECK_INTERNET_TEXT
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@Composable
fun TodayRecCocktails(
    navigateToDetail: (Int) -> Unit,
    randomRecList: List<Cocktail>,
) {
    HorizontalPager(
        count = randomRecList.size,
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp),
    ) { item ->
        Box {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(randomRecList[item].imgUrl)
                    .crossfade(true)
                    .build(),
                loading = {
                    ListCircularProgressIndicator(fraction = 0.2f)
                },
                contentDescription = "Random Rec Img",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(190.dp)
                    .clickable {
                        navigateToDetail(randomRecList[item].idx)
                    },
                error = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                            contentDescription = "Icon Error",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = CHECK_INTERNET_TEXT)
                    }
                },
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                Color_Default_Backgounrd_70,
                            ),
                        ),
                    ),
            )
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.TopEnd),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = randomRecList[item].krName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = randomRecList[item].enName, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                randomRecList[item].keyword.split(',').take(4).map {
                    Text(text = "#$it", fontSize = 13.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0x30ffffff))
                    .align(Alignment.BottomEnd),
                color = Color.Transparent,
            ) {
                Text(
                    text = "${item + 1} / 5",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(15.dp, 3.dp)
                        .background(Color.Transparent),
                )
            }
        }
    }
}

