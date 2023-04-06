package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalLineSpacer(heigt: Dp = 5.dp, color: Color = Color(0x40ffffff)) {
    Spacer(
        modifier = Modifier
            .height(heigt)
            .fillMaxWidth()
            .background(color = color),
    )
}
