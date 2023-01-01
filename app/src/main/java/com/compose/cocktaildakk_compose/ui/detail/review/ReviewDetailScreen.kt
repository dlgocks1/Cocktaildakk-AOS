@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.detail.review

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.ui.components.ImageContainer
import com.compose.cocktaildakk_compose.ui.detail.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.DetailViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW_WRITING
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ReviewDetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    idx: Int = 0
) {
    LaunchedEffect(Unit) {
        detailViewModel.getReview(idx)
        detailViewModel.getDetail(idx)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            BlurBackImg(cocktail = Cocktail())
            TopBar(detailViewModel.cocktailDetail.value.krName) {
                navController.popBackStack()
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .height(17.dp)
                .background(color = Color_Default_Backgounrd)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "리뷰 ${detailViewModel.reviewContents.size}개",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            navController.navigate(DETAIL_REVIEW_WRITING.format(idx))
                        }
                    ) {
                        Text(text = "리뷰 작성하기", fontSize = 16.sp, color = Color.White)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                            modifier = Modifier.size(16.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp)) // 최신 순 별점낮은순 넣기
                if (detailViewModel.reviewContents.isEmpty()) {
                    Box(Modifier.weight(1f)) {
                        ReviewEmpty()
                    }
                } else {
                    ReviewContent(detailViewModel)
                }
            }
        }
    }

}

@Composable
private fun ReviewContent(detailViewModel: DetailViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
        detailViewModel.reviewContents.forEach {
            ReviewContainer(it)
        }
    }
}

@Composable
private fun ReviewEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_sentiment_very_dissatisfied_24),
            contentDescription = null,
            modifier = Modifier.size(44.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "리뷰를 작성해주세요!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

}

@Composable
fun ReviewContainer(review: Review) {

    val pagerState = rememberPagerState(initialPage = 0)
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    id = if (review.userInfo.sex == "Unknown")
                        R.drawable.icon_app
                    else if (review.userInfo.sex == "Male")
                        R.drawable.img_male
                    else R.drawable.img_female
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
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
                    color = Color.White
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
                review.images[image]
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            HorizontalPagerIndicator(pagerState = pagerState)
        }
        Text(text = review.contents, color = Color.White)
    }
}

@Composable
private fun RankIcon(rank: Int, review: Review) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_star_24),
        contentDescription = null,
        modifier = Modifier
            .size(16.dp),
        tint = if (review.rankScore >= rank) Color.White else Color_White_70
    )
}


@Composable
fun TopBar(text: String, onClick: () -> Unit = {}) {
    Row(
        Modifier
            .padding(10.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = "Img Back",
            tint = Color.White,
            modifier = Modifier.clickable {
                onClick()
            }
        )
        Spacer(Modifier.width(5.dp))
        Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Preview
@Composable
fun ReviewPreview() {
    ReviewDetailScreen()
}