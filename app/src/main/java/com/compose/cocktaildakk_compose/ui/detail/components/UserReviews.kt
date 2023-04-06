package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Review

@Composable
fun UserReviews(
    idx: Int,
    navigateToReview: (Int) -> Unit,
    reviewContents: List<Review>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                navigateToReview(idx)
            },
        ) {
            Text(text = "리뷰 작성하기", fontSize = 18.sp, color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                contentDescription = "IC_ARROW_RIGHT",
                modifier = Modifier.size(34.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (reviewContents.isEmpty()) {
            EmptyReview()
        } else {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                reviewContents.map {
                    ReviewContent(idx, navigateToReview, it)
                }
            }
        }
    }
}
