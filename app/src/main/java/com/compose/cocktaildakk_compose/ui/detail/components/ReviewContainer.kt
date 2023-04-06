package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.FEMALE
import com.compose.cocktaildakk_compose.MALE
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.ui.components.ImageContainer
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReviewContainer(review: Review) {
    val pagerState = rememberPagerState(initialPage = 0)
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    id = when (review.userInfo.sex) {
                        MALE -> {
                            R.drawable.img_male
                        }
                        FEMALE -> {
                            R.drawable.img_female
                        }
                        else -> {
                            R.drawable.icon_app
                        }
                    },
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "${review.userInfo.nickname}" +
                            if (review.userInfo.age != -1) {
                                "  |  ${review.userInfo.age}살"
                            } else {
                                ""
                            },
                    fontSize = 18.sp,
                    color = Color.White,
                )
                Row {
                    repeat(5) {
                        RankIcon(it + 1, review)
                    }
                }
            }
        }
        HorizontalPager(count = review.images.size, state = pagerState) { image ->
            ImageContainer(
                Modifier
                    .width((LocalConfiguration.current.screenWidthDp - 40).dp)
                    .aspectRatio(1.0f)
                    .background(color = Color_Default_Backgounrd),
                review.images[image],
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            HorizontalPagerIndicator(pagerState = pagerState)
        }
        Text(text = review.contents, color = Color.White)
    }
}
