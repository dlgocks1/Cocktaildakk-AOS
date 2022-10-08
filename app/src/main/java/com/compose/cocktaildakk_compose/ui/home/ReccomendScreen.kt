@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
fun ReccomendScreen(navController: NavController, mainRecList: List<Cocktail>) {
  val pagerState = rememberPagerState()
  Column(modifier = Modifier.fillMaxSize()) {
    HorizontalPager(
      count = mainRecList.size,
      state = pagerState,
      modifier = Modifier
        .weight(1f)
        .padding(top = 20.dp),
      contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) { page ->
      Card(modifier = Modifier
        .graphicsLayer {
          val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
          lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
          ).also { scale ->
            scaleX = scale
            scaleY = scale
          }
          alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
          )
        }
        .clickable {
          navController.navigate("detail/${mainRecList[page].idx}")
        }) {
        Image(
          painter = painterResource(id = R.drawable.img_main_dummy),
          contentDescription = "Main_Rec_Img",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxSize()
        )
      }
    }
    HorizontalPagerIndicator(
      pagerState = pagerState,
      modifier = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(16.dp)
    )
  }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
  (1 - fraction) * start + fraction * stop
