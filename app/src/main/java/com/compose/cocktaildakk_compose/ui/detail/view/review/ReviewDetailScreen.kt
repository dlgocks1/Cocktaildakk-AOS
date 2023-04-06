package com.compose.cocktaildakk_compose.ui.detail.view.review

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.detail.DetailViewModel
import com.compose.cocktaildakk_compose.ui.detail.components.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.components.ReviewContainer
import com.compose.cocktaildakk_compose.ui.detail.components.TopBar
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW_WRITING

@Composable
fun ReviewDetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    idx: Int = 0,
) {
    LaunchedEffect(Unit) {
        detailViewModel.getReview(idx)
        detailViewModel.getDetail(idx)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            BlurBackImg("")
            TopBar(
                text = detailViewModel.cocktailDetail.value.krName
            ) {
                navController.popBackStack()
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .height(17.dp)
                .background(color = Color_Default_Backgounrd),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "리뷰 ${detailViewModel.reviewContents.size}개",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            navController.navigate(DETAIL_REVIEW_WRITING.format(idx))
                        },
                    ) {
                        Text(text = "리뷰 작성하기", fontSize = 16.sp, color = Color.White)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                            modifier = Modifier.size(16.dp),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp)) // 최신 순 별점 낮은 순 넣기

                if (detailViewModel.reviewContents.isEmpty()) {
                    Box(Modifier.weight(1f)) {
                        ReviewEmpty()
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                        detailViewModel.reviewContents.forEach { review ->
                            ReviewContainer(review = review)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_sentiment_very_dissatisfied_24),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = Color.White,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "리뷰를 작성해주세요!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}
