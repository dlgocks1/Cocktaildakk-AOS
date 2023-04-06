package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R

@Composable
fun EmptyReview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
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
