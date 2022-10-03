package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchButton() {
  OutlinedButton(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
      .height(40.dp),
    colors = ButtonDefaults.buttonColors(Color.White),
    onClick = { /*TODO*/ },
    shape = RoundedCornerShape(10.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Start
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_baseline_search_24),
        contentDescription = "searchIcon",
        tint = Color_Default_Backgounrd
      )
    }
  }
}