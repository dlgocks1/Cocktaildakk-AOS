package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.clickable
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
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchButton(onclick: () -> Unit) {
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "searchIcon",
                    tint = Color_Default_Backgounrd
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = VISIBLE_SEARCH_STR.value,
                    fontSize = 17.sp,
                    color = Color.Black
                )
            }
            if (VISIBLE_SEARCH_STR.value.isNotBlank()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = "Icon Close",
                    tint = Color_Default_Backgounrd,
                    modifier = Modifier.clickable {
                        VISIBLE_SEARCH_STR.value = ""
                    }
                )
            }

        }
    }
}