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
import androidx.compose.ui.layout.ContentScale
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
import com.compose.cocktaildakk_compose.ui.detail.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.DetailViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW_WRITING
import com.compose.cocktaildakk_compose.ui.utils.PermissionRequestScreen

@Composable
fun ReviewDetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    idx: Int = 0
) {
    LaunchedEffect(Unit) {
//        detailViewModel.getReview(idx)
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
                        text = "최근 리뷰 15개",
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
//                ReviewContent()
                Box(Modifier.weight(1f)) {
                    ReviewEmpty()
                }
            }
        }
    }

}

@Composable
private fun ReviewContent() {
    Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
        for (i in 0..2) {
            ReviewContainer()
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
        PermissionRequestScreen()
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
fun ReviewContainer() {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.img_male),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "닉네임 | 나이  ", fontSize = 18.sp, color = Color.White)
                Text(text = "별점 : 4개", fontSize = 16.sp, color = Color.White)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.img_main_dummy),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1.0f),
            contentScale = ContentScale.Crop
        )
        Text(text = "존맛탱 칵테일임", color = Color.White)
    }
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