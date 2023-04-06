package com.compose.cocktaildakk_compose.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_LightGreen

@Composable
fun SearchResultTopView(
    size: Int,
    reccomandOption: Boolean = false,
    changeRecommandOption: (Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (size == 0) {
                "검색결과가 없습니다."
            } else {
                "총 ${size}개의 검색 결과"
            },
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier.padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "나에게 맞는 추천 순 보기",
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Color_LightGreen,
                    uncheckedColor = Color_Cyan
                ),
                checked = reccomandOption,
                onCheckedChange = {
                    changeRecommandOption(it)
                })
        }
    }
}