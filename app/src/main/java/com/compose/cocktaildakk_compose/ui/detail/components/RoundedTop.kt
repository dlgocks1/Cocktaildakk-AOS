package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun RoundedTop() {
    Box(modifier = Modifier.height(20.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    3.dp,
                    Color.White,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                )
                .height(20.dp),
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                )
                .height(17.dp)
                .background(color = Color_Default_Backgounrd),
        )
    }
}