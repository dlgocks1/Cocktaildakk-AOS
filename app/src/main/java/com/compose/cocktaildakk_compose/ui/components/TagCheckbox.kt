@file:OptIn(ExperimentalMaterialApi::class)

package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan_70
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70

@Composable
fun TagCheckbox(
  isChecked: Boolean,
  modifier: Modifier = Modifier,
  onCheckChanged: () -> Unit = {},
  text: String
) {
  Card(
    onClick = { onCheckChanged() },
    modifier = modifier
      .padding(10.dp, 0.dp),
    backgroundColor = Color.Transparent,
    elevation = 0.dp,
    shape = RoundedCornerShape(10.dp),
    border = BorderStroke(1.dp, Color_Cyan),
  ) {
    Text(
      text = text,
      modifier = Modifier
        .background(color = if (isChecked) Color_Cyan else Color_Default_Backgounrd_70)
        .padding(20.dp, 5.dp),
      textAlign = TextAlign.Center,
      color = if (isChecked) Color.Black else Color.White,
      maxLines = 1
    )
  }

}