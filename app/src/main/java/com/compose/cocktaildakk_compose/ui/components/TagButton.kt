package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan

@Composable
fun TagButton(text: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color_Cyan, RoundedCornerShape(10.dp))
            .padding(10.dp, 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text.trim(),
            color = Color.White,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .clickable {
                }
        )
    }
}