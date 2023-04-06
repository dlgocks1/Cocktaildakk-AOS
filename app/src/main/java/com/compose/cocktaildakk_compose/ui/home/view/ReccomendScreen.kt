@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.CHECK_INTERNET_TEXT
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
fun ReccomendScreen(
    navigateToDetail: (Int) -> Unit,
    mainRecList: List<Cocktail>,
) {
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = mainRecList.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                    }
                    .clickable {
                        navigateToDetail(mainRecList[page].idx)
                    }
                    .background(color = Color.Transparent),
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mainRecList[page].imgUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                        ListCircularProgressIndicator(fraction = 0.2f)
                    },
                    contentDescription = stringResource(R.string.main_rec),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(color = Color_Default_Backgounrd)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Color_Default_Backgounrd),
                    error = {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                                contentDescription = "Icon Error",
                                modifier = Modifier.size(36.dp),
                                tint = Color.White,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = CHECK_INTERNET_TEXT)
                        }
                    },
                )
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop
