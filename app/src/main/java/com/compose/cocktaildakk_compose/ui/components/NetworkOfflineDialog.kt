package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.compose.cocktaildakk_compose.CHECK_INTERNET_TEXT
import com.compose.cocktaildakk_compose.INTERNET_NOT_CONNECTED_TEXT
import com.compose.cocktaildakk_compose.domain.model.NetworkState

@Composable
fun NetworkOfflineDialog(
    networkState: NetworkState,
) {
    if (networkState is NetworkState.NotConnected) {
        Dialog(
            onDismissRequest = {},
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = INTERNET_NOT_CONNECTED_TEXT,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = CHECK_INTERNET_TEXT, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            }
        }
    }
}
