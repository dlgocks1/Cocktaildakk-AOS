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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R

@Composable
fun TopBar(text: String, onClick: () -> Unit = {}) {
    Row(
        Modifier
            .padding(10.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = "Img Back",
            tint = Color.White,
            modifier = Modifier.clickable {
                onClick()
            },
        )
        Spacer(Modifier.width(5.dp))
        Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

