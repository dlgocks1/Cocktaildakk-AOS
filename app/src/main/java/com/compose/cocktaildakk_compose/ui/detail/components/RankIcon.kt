package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70

@Composable
fun RankIcon(rank: Int, review: Review) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_star_24),
        contentDescription = null,
        modifier = Modifier
            .size(16.dp),
        tint = if (review.rankScore >= rank) Color.White else Color_White_70,
    )
}