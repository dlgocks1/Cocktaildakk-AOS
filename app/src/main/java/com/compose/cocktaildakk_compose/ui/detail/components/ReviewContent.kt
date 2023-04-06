package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.ui.components.ImageContainer

@Composable
fun ReviewContent(
    idx: Int,
    navigateToReview: (Int) -> Unit,
    review: Review,
) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                BorderStroke(1.dp, Color.White),
                RoundedCornerShape(10.dp),
            )
            .clickable {
                navigateToReview(idx)
            },
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(
                text = "${review.userInfo.nickname}님",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(5.dp)) {
                ImageContainer(Modifier.size(80.dp), review.images.first())
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "별점 ${review.rankScore}점")
                    Text(text = review.contents.take(24), fontSize = 13.sp) // 최대 24글자까지만
                }
            }
        }
    }
}

