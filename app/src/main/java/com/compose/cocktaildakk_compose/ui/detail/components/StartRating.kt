package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70

@Composable
fun StarRating(cocktail: Cocktail, rankScore: Int, setRankScore: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = cocktail.krName, fontSize = 30.sp, color = Color.White)
        Text(text = cocktail.enName, fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "별을 클릭하여\n\'${cocktail.krName}\'에 대한 별점을 평가해 주세요.",
            fontSize = 16.sp,
            color = Color_White_70,
            textAlign = TextAlign.Center,
        )
        Rank(
            rankScore = rankScore,
            setRankScore = setRankScore,
        )
    }
}

@Composable
private fun Rank(rankScore: Int, setRankScore: (Int) -> Unit) {
    Row {
        repeat(5) {
            RankIcon(it + 1, setRankScore, rankScore)
        }
    }
}

@Composable
private fun RankIcon(rank: Int, setRankScore: (Int) -> Unit, rankScore: Int) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_star_24),
        contentDescription = null,
        modifier = Modifier
            .size(42.dp)
            .clickable {
                setRankScore(rank)
            },
        tint = if (rankScore >= rank) Color.White else Color_White_70,
    )
}
