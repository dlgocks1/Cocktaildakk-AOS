package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchButton(searchStr: String = "", onclick: () -> Unit) {
  OutlinedButton(
    modifier = Modifier
      .fillMaxWidth()
      .padding(20.dp)
      .height(40.dp),
    colors = ButtonDefaults.buttonColors(Color.White),
    onClick = { onclick() },
    shape = RoundedCornerShape(20.dp)
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
      Spacer(modifier = Modifier.width(10.dp))
      Text(text = searchStr, fontSize = 17.sp, color = Color.Black)
    }
  }
}